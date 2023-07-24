package com.example.bookservice.dto;

import com.example.bookservice.entity.Book;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryWiseBooks {

    private Long categoryId;

    private String categoryName;

    private List<Book> books;
}
