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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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

    public LinkedHashMap<String, List<Book>> fetchCategoryWiseBooks() {

        List<Book> books= getAllBooks();

        List<CategoryWiseBooks> cBooks= new LinkedList<>();

        LinkedHashMap<String, List<Book>> categoryBooks= new LinkedHashMap<>();

        try {
            for (Book book : books) {
                HttpHeaders headers = new HttpHeaders();
                HttpEntity<String> entity = new HttpEntity<String>(headers);
                ResponseEntity<Category> response = restTemplate.exchange(new URI("http://CATEGORY-SERVICE/category/get-category?id=" + book.getCategoryId()),
                        HttpMethod.GET, entity, Category.class);
                String categoryName=response.getBody().getCategoryName();
                if(categoryBooks.containsKey(categoryName)){
                    List<Book> bookList=categoryBooks.get(categoryName);
                    bookList.add(book);
                    categoryBooks.replace(categoryName, bookList);
                }else{
                    List<Book> bookList=new ArrayList<>();
                    bookList.add(book);
                    categoryBooks.put(categoryName,bookList);
                }
            }
        }
        catch(Exception e){
                System.out.println("Exception Occured");
            }

        return categoryBooks;

    }
}
