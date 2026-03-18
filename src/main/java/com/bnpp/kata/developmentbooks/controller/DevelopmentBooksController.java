package com.bnpp.kata.developmentbooks.controller;

import com.bnpp.kata.developmentbooks.model.Books;
import com.bnpp.kata.developmentbooks.model.OrderResponse;
import com.bnpp.kata.developmentbooks.service.DevelopmentBooksService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class DevelopmentBooksController implements CalculatePriceApi {

    private final DevelopmentBooksService developmentBooksService;

    @GetMapping("/getListOfBooks")
    public ResponseEntity<List<String>> getListOfBooks() {
        return ResponseEntity.ok(developmentBooksService.getListOfBooks());
    }

    public ResponseEntity<OrderResponse> calculateBookPriceApi(@Valid @RequestBody Books books) {
        return ResponseEntity.ok(developmentBooksService.calculateBookPrice(books.getBooks()));
    }
}
