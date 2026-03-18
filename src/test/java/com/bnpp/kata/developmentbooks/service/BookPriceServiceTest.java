package com.bnpp.kata.developmentbooks.service;

import com.bnpp.kata.developmentbooks.model.BookItems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.bnpp.kata.developmentbooks.constants.Constants.ZERO_DOUBLE;
import static com.bnpp.kata.developmentbooks.constants.Constants.ZERO_INT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookPriceServiceTest {

    private BookPriceService bookPriceService;
    private BookItems bookItems;
    private List<BookItems> bookItemsList;

    @BeforeEach
    public void setup(){
        bookPriceService=new BookPriceService();
        bookItemsList=new ArrayList<>();
    }

    @Test
    @DisplayName("should return base price for a single book without discount")
    public void calculateSingleBookPrice(){
        bookItemsList = List.of(new BookItems("Clean code",2));
        double price = bookPriceService.calculateBookPrice(bookItemsList);
        assertEquals(100.0,price);
    }

    @Test
    @DisplayName("should return zero when book title is empty")
    public void calculateSingleBookWithEmptyData(){

        double price = bookPriceService.calculateBookPrice(bookItemsList);
        assertEquals(ZERO_DOUBLE,price);
    }

    @Test
    @DisplayName("should return zero when quantity is zero")
    public void calculateSingleBookWithZeroQuantity(){
        bookItemsList = List.of(new BookItems("Clean code",0));
        double price = bookPriceService.calculateBookPrice(bookItemsList);
        assertEquals(ZERO_DOUBLE,price);
    }

    @Test
    @DisplayName("should return total price for two different books without discount")
    public void calculateTwoDifferentBooksPrice(){
        bookItemsList = List.of(new BookItems("Clean code",1),new BookItems("The Clean Coder", 1));
        double price = bookPriceService.calculateBookPrice(bookItemsList);
        assertEquals(100.0,price);
    }

    @Test
    @DisplayName("should return total price for three different books without discount")
    public void calculateThreeDifferentBooksPrice(){
        bookItemsList = List.of(new BookItems("Clean code",1),
                new BookItems("The Clean Coder",1),
                new BookItems("Clean Architecture",1));
        double price = bookPriceService.calculateBookPrice(bookItemsList);
        assertEquals(150.0,price);
    }

    @Test
    @DisplayName("should return total price for four different books without discount")
    public void calculateFourDifferentBooksPrice(){
        bookItemsList = List.of(new BookItems("Clean code",1),
                new BookItems("The Clean Coder",1),
                new BookItems("Clean Architecture",1),
                new BookItems("Test Driven Development by Example", 1));
        double price = bookPriceService.calculateBookPrice(bookItemsList);
        assertEquals(200.0,price);
    }
}
