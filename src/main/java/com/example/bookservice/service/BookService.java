package com.example.bookservice.service;

import com.example.bookservice.dto.BookResponse;
import com.example.bookservice.dto.Category;
import com.example.bookservice.entity.Book;
import com.example.bookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.*;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired

    private RestTemplate restTemplate;
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public BookResponse getBook(Long id) {
        BookResponse bookResp = new BookResponse();
        Book book=bookRepository.getById(id);
        System.out.println(book.getId());
        bookResp.setBook(book);
        HttpHeaders headers = new HttpHeaders();
        Category category= restTemplate.getForObject("http://CATEGORY-SERVICE/category/get-category?id="+book.getCategoryId(),
                Category.class);
        bookResp.setCategory(category);
        return bookResp;
    }
}
