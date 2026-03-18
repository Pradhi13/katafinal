package com.bnpp.kata.developmentbooks.service;

import com.bnpp.kata.developmentbooks.constants.BookType;
import com.bnpp.kata.developmentbooks.model.BookItems;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.bnpp.kata.developmentbooks.constants.Constants.*;

@Service
public class BookPriceService {

    public double calculateBookPrice(BookItems bookItems){
        if(bookItems.getTitle()==null||bookItems.getTitle().trim().isEmpty()||bookItems.getQuantity()<=ZERO_INT){
            return ZERO_DOUBLE;
        }
        return BASE_PRICE * bookItems.getQuantity();
    }

    public List<String> getListOfBooks() {
        return Arrays.stream(BookType.values()).map(BookType::getTitle).collect(Collectors.toList());
    }
}
