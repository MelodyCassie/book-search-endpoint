package com.example.book.search.services;

import com.example.book.search.dtos.request.LibrarianRegisterRequest;
import com.example.book.search.dtos.response.LibrarianRegisterResponse;
import com.example.book.search.dtos.response.SearchBookResponse;
import com.example.book.search.exceptions.BookNotFoundException;
import com.example.book.search.exceptions.LibrarianNotFoundException;
import com.example.book.search.models.Books;
import com.example.book.search.models.Librarian;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class LibrarianServiceTest {
    @Autowired
    LibrarianService librarianService;
    @Test
    void testThatLibrarianCanRegister(){
        LibrarianRegisterRequest librarianRegisterRequest = new LibrarianRegisterRequest();
        librarianRegisterRequest.setPassword("mel123");
        librarianRegisterRequest.setUsername("melOluchi");

        LibrarianRegisterResponse librarianRegisterResponse = librarianService.register(librarianRegisterRequest);
        assertThat(librarianRegisterResponse).isNotNull();
    }

    @Test
    void testThatLibrarianCanSearchForBooks() throws IOException, LibrarianNotFoundException, BookNotFoundException {
        SearchBookResponse searchBookResponse = librarianService.search(1L,"Romeo and Juliet");
        assertThat(searchBookResponse).isNotNull();

    }
    @Test
    void testThatLibrarianHasBooksInReadingList() throws LibrarianNotFoundException {
        List<Books> readingList = librarianService.getReadingList(1L);
        assertThat(readingList).isNotNull();
        log.info("reading list -> {}",readingList);
    }
}