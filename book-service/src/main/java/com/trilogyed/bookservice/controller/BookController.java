package com.trilogyed.bookservice.controller;

import com.trilogyed.bookservice.dao.BookRepo;
import com.trilogyed.bookservice.model.Book;
import com.trilogyed.bookservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepo bookRepo;

    @GetMapping("/{isbn}")
    public Book getByIsbn(@PathVariable String isbn) throws ClientErrorException {
        return bookRepo.findById(isbn)
                .orElseThrow(() -> new ClientErrorException(HttpStatus.NOT_FOUND, "book with isbn " + isbn + " does not exist"));
    }

    @PostMapping("/addbook")
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@RequestBody @Valid Book book) {
        return bookRepo.save(book);
    }

    @GetMapping("/buybook/{isbn}")
    public Integer buyBook(@PathVariable String isbn) throws ClientErrorException {
        return bookService.buyBook(isbn)
                .orElseThrow(() -> new ClientErrorException(HttpStatus.NOT_FOUND, "book with isbn " + isbn + " does not exist"));
    }
}
