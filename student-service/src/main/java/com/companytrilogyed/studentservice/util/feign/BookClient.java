package com.companytrilogyed.studentservice.util.feign;

import com.companytrilogyed.studentservice.model.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(value = "book-service", decode404 = true)
public interface BookClient {

    @GetMapping("/book/{isbn}")
    public Optional<Book> getBook(@PathVariable String isbn);

    @GetMapping("/book/buybook/{isbn}")
    public Optional<Integer> buyBook(@PathVariable String isbn);

}
