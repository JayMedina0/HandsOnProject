package com.trilogyed.bookservice.service;

import com.trilogyed.bookservice.dao.BookRepo;
import com.trilogyed.bookservice.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class BookService {
    private BookRepo repo;

    @Autowired
    public BookService(BookRepo repo) {
        this.repo = repo;
    }

    /**
     * Decrement book quantity and return
     * new quantity
     * @param isbn book isbn
     * @return optional of new quantity if isbn exists
     * or empty optional
     */
    public Optional<Integer> buyBook(String isbn) {
        return repo.findById(isbn)
                .map(b -> {
                    b.setQuantity(b.getQuantity() - 1);
                    return repo.save(b);
                })
                .map(Book::getQuantity);
    }
}
