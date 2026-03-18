package com.bnpp.kata.developmentbooks.service;

import com.bnpp.kata.developmentbooks.constants.Constants;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.bnpp.kata.developmentbooks.constants.Constants.*;

@Service
public class BookPriceService {

    public double calculateBookPrice(String book,int quantity){
        if(book==null||book.trim().isEmpty()||quantity<=ZERO_INT){
            return ZERO_DOUBLE;
        }
        return BASE_PRICE * quantity;
    }

    public List<String> getListOfBooks() {
        return Constants.BOOK_LIST;
    }
}
