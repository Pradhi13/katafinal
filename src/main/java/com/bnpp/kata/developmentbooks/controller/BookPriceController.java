package com.bnpp.kata.developmentbooks.controller;

import com.bnpp.kata.developmentbooks.service.BookPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookPriceController {

    private final BookPriceService bookPriceService;

    @GetMapping("/getListOfBooks")
    public ResponseEntity<List<String>> getListOfBooks() {
        return ResponseEntity.ok(bookPriceService.getListOfBooks());
    }
}
