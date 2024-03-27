package com.example.book.search.repositories;

import com.example.book.search.models.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibrarianRepository extends JpaRepository<Librarian, Long> {
}
