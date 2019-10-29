package com.trilogyed.bookservice.service;

import com.trilogyed.bookservice.dao.BookRepo;
import com.trilogyed.bookservice.model.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
    @InjectMocks
    private BookService service;
    @Mock
    private BookRepo repo;

    @Before
    public void setUp() throws Exception {
        reset(repo);
    }

    @Test
    public void buyBook_withExistingIsbn_shouldReturnOptionalOfNewBookQuantity() {
        Book book = new Book(
                "title",
                "author",
                "isbn",
                10
        );
        when(repo.findById("isbn")).thenReturn(Optional.of(book));
        when(repo.save(any()))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        Integer expectedQuantity = 9;

        Integer actualQuantity = service.buyBook("isbn").orElse(null);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void buyBook_withNotExistingIsbn_shouldReturnEmptyOptional() {
        Integer actualQuantity = service.buyBook("isbn").orElse(null);
        assertNull(actualQuantity);
    }
}