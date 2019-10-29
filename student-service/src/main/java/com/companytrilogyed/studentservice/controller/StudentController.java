package com.companytrilogyed.studentservice.controller;

import com.companytrilogyed.studentservice.model.Book;
import com.companytrilogyed.studentservice.util.feign.BookClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class StudentController {

    @Autowired
    BookClient client;

    @GetMapping("/book/{isbn}")
    public Book getBook (@PathVariable String isbn){
        return client.getBook(isbn).orElse(null);
    }

    @GetMapping("/studentfe/buybook/{isbn}")
    public Integer buyBook (@PathVariable String isbn){
        return client.buyBook(isbn).orElse(null);
    }


}
