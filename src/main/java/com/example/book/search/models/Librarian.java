package com.example.book.search.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Librarian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "librarian_reading_list",joinColumns = @JoinColumn(name = "book_id"),inverseJoinColumns = @JoinColumn(name = "librarian_id")
    )
  private List<Books> readingList;

}
