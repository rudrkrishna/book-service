package com.example.bookservice.dto;

import com.example.bookservice.entity.Book;
import lombok.Data;

@Data
public class BookResponse {

    private Book book;

    private Category category;


}
