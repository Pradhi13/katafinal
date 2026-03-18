package com.bnpp.kata.developmentbooks.service;

import com.bnpp.kata.developmentbooks.constants.BookType;
import com.bnpp.kata.developmentbooks.model.BookItems;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.bnpp.kata.developmentbooks.constants.Constants.*;

@Service
public class DevelopmentBooksService {

    public double calculateBookPrice(@NotEmpty(message = "Book list cannot be empty")
                                     List<@Valid BookItems> bookItemsList) {
        int[] quantities = bookItemsList.stream().mapToInt(BookItems::getQuantity).toArray();
        return findBestPrice(quantities);
    }
    private double findBestPrice(int[] quantities) {

        if (Arrays.stream(quantities).allMatch(quantityValue -> quantityValue == ZERO_INT)) {
            return ZERO_DOUBLE;

        }
        return IntStream.rangeClosed(ONE, quantities.length)
                .mapToDouble(size -> calculateGroupPrice(size, quantities))
                .filter(price -> price > ZERO_INT)
                .min()
                .orElse(Double.MAX_VALUE);
    }
    private double calculateGroupPrice(int size, int[] quantities) {
        int[] next = Arrays.copyOf(quantities, quantities.length);
        int count = (int) java.util.stream.IntStream.range(ZERO_INT, next.length)
                .filter(i -> next[i] > ZERO_INT)
                .limit(size)
                .peek(i -> next[i]--)
                .count();
        if (count != size) {
            return ZERO_DOUBLE;
        }
        double price = size * BASE_PRICE * (1 - DISCOUNT.get(size));
        return price + findBestPrice(next);
    }

    public List<String> getListOfBooks() {
        return Arrays.stream(BookType.values()).map(BookType::getTitle).collect(Collectors.toList());
    }
}
