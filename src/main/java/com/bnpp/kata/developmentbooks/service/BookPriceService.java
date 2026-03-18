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

    public double calculateBookPrice(List<BookItems> bookItemsList){
        if(bookItemsList==null||bookItemsList.isEmpty()){
            return ZERO_DOUBLE;
        }
        long uniqueBooks = bookItemsList.stream()
                .map(BookItems::getTitle)
                .collect(Collectors.toSet())
                .size();
        double totalBooks = bookItemsList.stream()
                .mapToDouble(BookItems::getQuantity)
                .sum();
        if(totalBooks<=0)
            return ZERO_DOUBLE;
        return uniqueBooks * totalBooks * BASE_PRICE;
    }

    public List<String> getListOfBooks() {
        return Arrays.stream(BookType.values()).map(BookType::getTitle).collect(Collectors.toList());
    }
}
