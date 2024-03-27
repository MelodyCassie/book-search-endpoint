package com.example.book.search.services;

import com.example.book.search.dtos.request.LibrarianRegisterRequest;
import com.example.book.search.dtos.response.LibrarianRegisterResponse;
import com.example.book.search.dtos.response.SearchBookResponse;
import com.example.book.search.exceptions.BookNotFoundException;
import com.example.book.search.exceptions.LibrarianNotFoundException;
import com.example.book.search.models.Books;

import java.io.IOException;
import java.util.List;

public interface LibrarianService {

   List<Books> getReadingList(long l) throws LibrarianNotFoundException;


   LibrarianRegisterResponse register(LibrarianRegisterRequest librarianRegisterRequest);
   SearchBookResponse search(Long id, String title) throws IOException, LibrarianNotFoundException, BookNotFoundException;
}
