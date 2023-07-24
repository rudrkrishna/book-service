package com.example.bookservice.controller;

import com.example.bookservice.dto.BookResponse;
import com.example.bookservice.entity.Book;
import com.example.bookservice.service.BookService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BookController {
    @Autowired
    private BookService bookService;
    @PostMapping("/add-book")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        return new ResponseEntity<>(bookService.addBook(book), HttpStatus.CREATED);
    }
    @GetMapping("/get-book")
    public ResponseEntity<BookResponse> getBook(@RequestParam Long id){
        return new ResponseEntity<>(bookService.getBook(id), HttpStatus.OK);
    }
}
