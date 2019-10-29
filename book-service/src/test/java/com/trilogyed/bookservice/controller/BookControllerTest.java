package com.trilogyed.bookservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.bookservice.dao.BookRepo;
import com.trilogyed.bookservice.model.Book;
import com.trilogyed.bookservice.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private BookService bookService;
    @MockBean
    private BookRepo bookRepo;

    private String toJsonString(Object o) throws JsonProcessingException {
        return mapper.writeValueAsString(o);
    }

    @Before
    public void setUp() throws Exception {
        reset(bookService, bookRepo);
    }

    @Test
    public void getByIsbn_withExitingIsbn_shouldReturnStatus200WithBook() throws Exception {
        Book book = new Book(
                "title",
                "author",
                "isbn",
                10
        );
        when(bookRepo.findById(book.getIsbn())).thenReturn(Optional.of(book));
        String expectedJson = mapper.writeValueAsString(book);

        mockMvc.perform(get("/book/isbn"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void getByIsbn_withNotExistingIsbn_shouldReturnStatus404() throws Exception {
        mockMvc.perform(get("/book/isbn"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addBook_withNotExistingBook_shouldReturnStatus201WithCreatedBook() throws Exception {
        Book book = new Book(
                "title",
                "author",
                "isbn",
                10
        );

        when(bookRepo.save(book)).thenReturn(book);

        mockMvc.perform(post("/book/addbook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(book)))
                .andExpect(status().isCreated())
                .andExpect(content().json(toJsonString(book)));
    }

//    @Test
//    public void addBook_withExistingBook_shouldReturnStatus422() throws Exception {
//        Book book = new Book(
//                "title",
//                "author",
//                "isbn",
//                10
//        );
//        fail();
//    }

    @Test
    public void buyBook_withExistingIsbn_shouldReturnStatus200WithNewQuantity() throws Exception {
        String isbn = "isbn";
        when(bookService.buyBook(isbn)).thenReturn(Optional.of(10));

        mockMvc.perform(get("/book/buybook/isbn"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }

    @Test
    public void buyBook_withNotExistingIsbn_shouldReturnStatus404() throws Exception {
        mockMvc.perform(get("/book/buybook/isbn"))
                .andExpect(status().isNotFound());
    }
}