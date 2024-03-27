package com.example.book.search.dtos.response;

import com.example.book.search.models.Books;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@ToString
public class SearchBookResponse {

    private List<Books> books;

}
