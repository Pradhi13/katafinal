package com.bnpp.kata.developmentbooks.service;

import com.bnpp.kata.developmentbooks.constants.BookType;
import com.bnpp.kata.developmentbooks.model.BookItems;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.bnpp.kata.developmentbooks.constants.Constants.*;

@Service
public class BookPriceService {

    public double calculateBookPrice(@NotEmpty(message = "Book list cannot be empty")
                                     List<@Valid BookItems> bookItemsList){

        Map<String,Long> titleCounts = bookItemsList.stream().collect(Collectors.groupingBy(BookItems::getTitle,Collectors.counting()));

        long uniqueBooks = titleCounts.size();

        double discountSetPrice = uniqueBooks * BASE_PRICE * (1-DISCOUNTS[(int) uniqueBooks]);

        long totalQuantity = bookItemsList.stream()
                .mapToLong(BookItems::getQuantity).sum();
        double extraCopies = (totalQuantity-uniqueBooks)*BASE_PRICE;
        if(totalQuantity<=0){
            return ZERO_DOUBLE;
        }
        return discountSetPrice + extraCopies;
    }

    public List<String> getListOfBooks() {
        return Arrays.stream(BookType.values()).map(BookType::getTitle).collect(Collectors.toList());
    }
}
