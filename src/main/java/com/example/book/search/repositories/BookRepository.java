package com.example.book.search.repositories;

import com.example.book.search.models.Books;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Books, Long> {
}
