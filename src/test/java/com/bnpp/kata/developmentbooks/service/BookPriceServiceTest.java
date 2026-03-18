package com.bnpp.kata.developmentbooks.service;

import com.bnpp.kata.developmentbooks.model.BookItems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.bnpp.kata.developmentbooks.constants.Constants.ZERO_DOUBLE;
import static com.bnpp.kata.developmentbooks.constants.Constants.ZERO_INT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookPriceServiceTest {

    private BookPriceService bookPriceService;
    private BookItems bookItems;

    @BeforeEach
    public void setup(){
        bookPriceService=new BookPriceService();
        bookItems=new BookItems();
    }

    @Test
    @DisplayName("should return base price for a single book without discount")
    public void calculateSingleBookPrice(){
        bookItems.setTitle("Clean Code");
        bookItems.setQuantity(2);
        double price = bookPriceService.calculateBookPrice(bookItems);
        assertEquals(100.0,price);
    }

    @Test
    @DisplayName("should return zero when book title is empty")
    public void calculateSingleBookWithEmptyData(){
        bookItems.setTitle("");
        bookItems.setQuantity(2);
        double price = bookPriceService.calculateBookPrice(bookItems);
        assertEquals(ZERO_DOUBLE,price);
    }

    @Test
    @DisplayName("should return zero when quantity is zero")
    public void calculateSingleBookWithZeroQuantity(){
        bookItems.setTitle("Clean Code");
        bookItems.setQuantity(0);
        double price = bookPriceService.calculateBookPrice(bookItems);
        assertEquals(ZERO_DOUBLE,price);
    }
}
