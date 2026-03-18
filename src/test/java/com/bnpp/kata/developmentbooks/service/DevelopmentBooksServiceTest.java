package com.bnpp.kata.developmentbooks.service;

import com.bnpp.kata.developmentbooks.model.BookItems;
import com.bnpp.kata.developmentbooks.model.OrderResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.bnpp.kata.developmentbooks.constants.Constants.ZERO_DOUBLE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DevelopmentBooksServiceTest {

    private DevelopmentBooksService developmentBooksService;
    private BookItems bookItems;
    private List<BookItems> bookItemsList;

    @BeforeEach
    public void setup(){
        developmentBooksService =new DevelopmentBooksService();
        bookItemsList=new ArrayList<>();
    }

    @Test
    @DisplayName("should return base price for a single book without discount")
    public void calculateSingleBookPrice(){
        bookItemsList = List.of(new BookItems("Clean code",2));
        OrderResponse orderResponse= developmentBooksService.calculateBookPrice(bookItemsList);
        assertEquals(100.0,orderResponse.getDiscountedPrice());
    }

    @Test
    @DisplayName("should return zero when book title is empty")
    public void calculateSingleBookWithEmptyData(){

        OrderResponse orderResponse= developmentBooksService.calculateBookPrice(bookItemsList);
        assertEquals(ZERO_DOUBLE,orderResponse.getDiscountedPrice());
    }

    @Test
    @DisplayName("should return zero when quantity is zero")
    public void calculateSingleBookWithZeroQuantity(){
        bookItemsList = List.of(new BookItems("Clean code",0));
        OrderResponse orderResponse= developmentBooksService.calculateBookPrice(bookItemsList);
        assertEquals(ZERO_DOUBLE, orderResponse.getDiscountedPrice());
    }

    @Test
    @DisplayName("should return total price for two different books without discount")
    public void calculateTwoDifferentBooksPrice(){
        bookItemsList = List.of(new BookItems("Clean code",1),new BookItems("The Clean Coder", 1));
        OrderResponse orderResponse= developmentBooksService.calculateBookPrice(bookItemsList);
        assertEquals(95.0, orderResponse.getDiscountedPrice());
    }

    @Test
    @DisplayName("should return total price for three different books without discount")
    public void calculateThreeDifferentBooksPrice(){
        bookItemsList = List.of(new BookItems("Clean code",1),
                new BookItems("The Clean Coder",1),
                new BookItems("Clean Architecture",1));
        OrderResponse orderResponse= developmentBooksService.calculateBookPrice(bookItemsList);
        assertEquals(135.0, orderResponse.getDiscountedPrice());
    }

    @Test
    @DisplayName("should return total price for four different books without discount")
    public void calculateFourDifferentBooksPrice(){
        bookItemsList = List.of(new BookItems("Clean code",1),
                new BookItems("The Clean Coder",1),
                new BookItems("Clean Architecture",1),
                new BookItems("Test Driven Development by Example", 1));
        OrderResponse orderResponse= developmentBooksService.calculateBookPrice(bookItemsList);
        assertEquals(160.0, orderResponse.getDiscountedPrice());
    }

    @Test
    @DisplayName("should return total price for five different books without discount")
    public void calculateFiveDifferentBooksPrice(){
        bookItemsList = List.of(new BookItems("Clean code",1),
                new BookItems("The Clean Coder",1),
                new BookItems("Clean Architecture",1),
                new BookItems("Test Driven Development by Example", 1),
                new BookItems("Working Effectively With Legacy Code", 1));
        OrderResponse orderResponse= developmentBooksService.calculateBookPrice(bookItemsList);
        assertEquals(187.5, orderResponse.getDiscountedPrice());
    }

    @Test
    @DisplayName("should return total price for 2 quantity of same book without discount")
    public void calculateTwoQuantityOfSameBookPrice(){
        bookItemsList = List.of(new BookItems("Clean code",2));
        OrderResponse orderResponse= developmentBooksService.calculateBookPrice(bookItemsList);
        assertEquals(100.0, orderResponse.getDiscountedPrice());
    }

    @Test
    @DisplayName("should return total price for multiple quantity of different book with discount")
    public void calculateMultipleQuantityOfDifferentBookPrice(){
        bookItemsList = List.of(new BookItems("Clean code",2),
                new BookItems("The Clean Coder",3));
        OrderResponse orderResponse= developmentBooksService.calculateBookPrice(bookItemsList);
        assertEquals(240.0, orderResponse.getDiscountedPrice());
    }

    @Test
    @DisplayName("should return total price for three books with two extra copy ")
    void calculatethreeBooksTwoExtraCopyDiscountsUniqueOnly() {
        bookItemsList = List.of(new BookItems("Clean code", 2),
                new BookItems("The Clean Coder", 2),
                new BookItems("Clean Architecture", 1));

        OrderResponse orderResponse= developmentBooksService.calculateBookPrice(bookItemsList);
        assertEquals(230.0, orderResponse.getDiscountedPrice());
    }

    @Test
    @DisplayName("should return best price for four books with two extra copy")
    void calculateFourBooksTwoExtraCopyDiscountsUniqueOnly() {
        bookItemsList = List.of(new BookItems("Clean code", 2),
                new BookItems("The Clean Coder", 2),
                new BookItems("Clean Architecture", 1),
                new BookItems("Test Driven Development by Example", 1));

        OrderResponse orderResponse= developmentBooksService.calculateBookPrice(bookItemsList);
        assertEquals(255.0, orderResponse.getDiscountedPrice());
    }

    @Test
    @DisplayName("should return best price for given five books combination")
    public void calculatePossibleCombination() {
        bookItemsList = List.of(new BookItems("Clean code", 2),
                new BookItems("The Clean Coder", 2),
                new BookItems("Clean Architecture", 2),
                new BookItems("Test Driven Development by Example", 1),
                new BookItems("Working Effectively With Legacy Code",1));
        OrderResponse orderResponse= developmentBooksService.calculateBookPrice(bookItemsList);
        assertEquals(320.0, orderResponse.getDiscountedPrice());
    }

    @Test
    public void calculateActualPrice() {
        bookItemsList = List.of(new BookItems("Clean code", 2),
                new BookItems("The Clean Coder", 2),
                new BookItems("Clean Architecture", 2),
                new BookItems("Test Driven Development by Example", 1),
                new BookItems("Working Effectively With Legacy Code",1));
        OrderResponse reponse = developmentBooksService.calculateBookPrice(bookItemsList);
        assertEquals(400, reponse.getTotalPrice());
    }





}
