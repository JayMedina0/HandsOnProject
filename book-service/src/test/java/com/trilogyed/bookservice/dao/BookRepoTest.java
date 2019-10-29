package com.trilogyed.bookservice.dao;

import com.trilogyed.bookservice.model.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookRepoTest {
    @Autowired
    private BookRepo repo;

    @Before
    public void setUp() throws Exception {
        repo.deleteAll();
    }

    @Test
    public void shouldSaveAndFindById() {
        Book book = new Book(
                "title",
                "author",
                "isbn",
                5
        );
        Book saved = repo.save(book);
        Book found = repo.findById("isbn").orElse(null);

        assertEquals(saved, found);
    }
}