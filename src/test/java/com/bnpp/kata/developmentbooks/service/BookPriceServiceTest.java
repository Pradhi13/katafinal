package com.bnpp.kata.developmentbooks.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookPriceServiceTest {

    private BookPriceService bookPriceService;

    @BeforeEach
    public void setup(){
        bookPriceService=new BookPriceService();
    }

    @Test
    @DisplayName("should return base price for a single book without discount")
    public void calculateSingleBookPrice(){
        double price = bookPriceService.calculateBookPrice("Clean Code",2);
        assertEquals(100.0,price);
    }
}
