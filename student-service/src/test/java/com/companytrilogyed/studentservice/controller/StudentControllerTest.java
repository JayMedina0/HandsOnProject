package com.companytrilogyed.studentservice.controller;

import com.companytrilogyed.studentservice.model.Book;
import com.companytrilogyed.studentservice.util.feign.BookClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
@RunWith(SpringRunner.class)
public class StudentControllerTest {

    @MockBean
    BookClient client;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    String toJsonString(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    @Before
    public void setUp() throws Exception {
        reset(client);
    }

    @Test
    public void getBook() throws Exception {
        Book book = new Book("title","author","isbn",1);

        when(client.getBook("isbn")).thenReturn(Optional.of(book));

        mockMvc.perform(get("/book/isbn"))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(book)));

    }

    @Test
    public void buyBook() throws Exception{

        String isbn = "isbn";

        when(client.buyBook(isbn)).thenReturn(Optional.of(1));

        mockMvc.perform(get("/studentfe/buybook/isbn"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

    }

    @Test
    public void testGetById404() throws Exception{

        mockMvc.perform(get("/book/isbn"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void testBuybook404() throws Exception{

        mockMvc.perform(get("/studentfe/buybook/isbn"))
                .andExpect(status().isNotFound());

    }
}