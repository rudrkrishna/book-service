package com.example.bookservice.service;

import com.example.bookservice.dto.BookResponse;
import com.example.bookservice.dto.Category;
import com.example.bookservice.dto.CategoryWiseBooks;
import com.example.bookservice.entity.Book;
import com.example.bookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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

        try {
            Book book = bookRepository.getById(id);
            System.out.println(book.getId());
            bookResp.setBook(book);
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<String>(headers);
            ResponseEntity<Category> category = restTemplate.exchange(new URI("http://CATEGORY-SERVICE/category/get-category?id=" + book.getCategoryId()),
                    HttpMethod.GET, entity, Category.class);
            bookResp.setCategory(category.getBody());
        }catch (Exception e){
            System.out.println("Exception Occured");
        }
        return bookResp;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<CategoryWiseBooks> fetchCategoryWiseBooks() {

        List<Book> books= getAllBooks();

        List<CategoryWiseBooks> cBooks= null;

        try {
            for (Book book : books) {
                System.out.println("Inside For loop");
                HttpHeaders headers = new HttpHeaders();
                HttpEntity<String> entity = new HttpEntity<String>(headers);
                ResponseEntity<Category> response = restTemplate.exchange(new URI("http://CATEGORY-SERVICE/category/get-category?id=" + book.getCategoryId()),
                        HttpMethod.GET, entity, Category.class);
                System.out.println(response.getBody());
                CategoryWiseBooks cBook = new CategoryWiseBooks();
                cBook.setCategoryId(book.getCategoryId());
                cBook.setCategoryName(response.getBody().getCategoryName());
                cBook.setBooks(bookRepository.findByCategoryId(book.getCategoryId()));
                cBooks.add(cBook);
            }
        }
        catch(Exception e){
                System.out.println("Exception Occured");
            }

        return cBooks;




    }
}
