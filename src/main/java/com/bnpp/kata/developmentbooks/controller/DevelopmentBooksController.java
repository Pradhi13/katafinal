package com.bnpp.kata.developmentbooks.controller;

import com.bnpp.kata.developmentbooks.model.Books;
import com.bnpp.kata.developmentbooks.service.DevelopmentBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class DevelopmentBooksController {

    private final DevelopmentBooksService developmentBooksService;

    @GetMapping("/getListOfBooks")
    public ResponseEntity<List<String>> getListOfBooks() {
        return ResponseEntity.ok(developmentBooksService.getListOfBooks());
    }

    @PostMapping("/calculatePrice")
    public double totalOrderDetails(@RequestBody Books books) {
        return developmentBooksService.calculateBookPrice(books.getBooks());

    }
}
