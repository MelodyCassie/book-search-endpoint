package com.example.book.search.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;
@Entity
@Getter
@Setter
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String bookId;
    @ElementCollection
    @Fetch(FetchMode.JOIN)
    private List<String> authors;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> subject;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> bookshelves;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> languages;



    @Override
    public String toString() {
        return "book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", subject=" + subject +
                ", bookshelves=" + bookshelves +
                ", languages=" + languages +
                '}';
    }
}
