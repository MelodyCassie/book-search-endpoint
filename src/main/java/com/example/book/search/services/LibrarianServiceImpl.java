package com.example.book.search.services;

import com.example.book.search.dtos.request.LibrarianRegisterRequest;
import com.example.book.search.dtos.response.LibrarianRegisterResponse;
import com.example.book.search.dtos.response.SearchBookResponse;
import com.example.book.search.exceptions.BookNotFoundException;
import com.example.book.search.exceptions.LibrarianNotFoundException;
import com.example.book.search.models.Books;
import com.example.book.search.models.Librarian;
import com.example.book.search.repositories.BookRepository;
import com.example.book.search.repositories.LibrarianRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LibrarianServiceImpl implements LibrarianService{
    private final LibrarianRepository librarianRepository;
    private final BookRepository bookRepository;

    @Override
    public List<Books> getReadingList(long id) throws LibrarianNotFoundException {
        Librarian librarian = findBy(id);
        return librarian.getReadingList();
    }

    @Override
    public LibrarianRegisterResponse register(LibrarianRegisterRequest librarianRegisterRequest) {
        Librarian librarian = new Librarian();
        librarian.setUsername(librarianRegisterRequest.getUsername());
        librarian.setPassword(librarianRegisterRequest.getPassword());
        librarianRepository.save(librarian);
        LibrarianRegisterResponse librarianRegisterResponse = new LibrarianRegisterResponse();
        librarianRegisterResponse.setId(librarian.getId());
        return librarianRegisterResponse;
    }

    @Override
    public SearchBookResponse search(Long id, String title) throws IOException, LibrarianNotFoundException, BookNotFoundException {
        if (title.contains(" ")){
            title = title.replace(" ","%20");
        }
        String url = "https://gutendex.com/books?search="+ title;
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
        String jsonResponse = EntityUtils.toString(closeableHttpResponse.getEntity());
        closeableHttpClient.close();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        JsonNode result = jsonNode.get("results");
        SearchBookResponse searchBookResponse = new SearchBookResponse();
        for (JsonNode bookItems: result
             ) {
            String bookId = bookItems.get("id").asText();
            String bookTitle = bookItems.get("title").asText();
            List<String> bookshelves = new ArrayList<>();
            for (JsonNode bookShelvesNode : bookItems.get("bookshelves")){
                bookshelves.add(bookShelvesNode.asText());
            }
            List<String> languages = new ArrayList<>();
            for (JsonNode languageNode : bookItems.get("languages")){
                languages.add(languageNode.asText());
            }
            List<String> subjects = new ArrayList<>();
            for (JsonNode subjectNode : bookItems.get("subjects")){
                subjects.add(subjectNode.asText());
            }
            List<String> authors = new ArrayList<>();
            for(JsonNode authorNode : bookItems.get("authors")){
                authors.add("Name: "+ authorNode.get("name").asText());
                authors.add("Birth Year: "+authorNode.get("birth_year").asText());
                authors.add("Death Year: " + authorNode.get("death_year").asText());
            }

            Books books = new Books();
            books.setBookId(bookId);
            books.setTitle(bookTitle);
            books.setBookshelves(bookshelves);
            books.setLanguages(languages);
            books.setSubject(subjects);
            books.setAuthors(authors);
            bookRepository.save(books);


            Librarian librarian = findBy(id);
            librarian.getReadingList().add(books);

            librarianRepository.save(librarian);
            List<Books> books1 = new ArrayList<>();
            books1.add(books);
            searchBookResponse.setBooks(books1);



        }
        if(searchBookResponse.getBooks() == null){
            throw new BookNotFoundException("Books Not Found");

        }
        return searchBookResponse;
    }
    private  Librarian findBy(Long id) throws LibrarianNotFoundException {

        return librarianRepository.findById(id).orElseThrow(()-> new LibrarianNotFoundException("Librarian Not Found"));

    }


}
