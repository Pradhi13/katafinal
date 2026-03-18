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
                                     List<@Valid BookItems> bookItemsList) {
        int[] quantities = bookItemsList.stream().mapToInt(BookItems::getQuantity).toArray();
        return findBestPrice(quantities);
    }
    private double findBestPrice(int[] quantities) {

        if (Arrays.stream(quantities).allMatch(quantityValue -> quantityValue == ZERO_INT)) {
            return ZERO_DOUBLE;

        }
        double minPrice = Double.MAX_VALUE;
        for (int size = ONE; size <= quantities.length; size++) {

            int[] next = Arrays.copyOf(quantities, quantities.length);
            int count = ZERO_INT;

            for (int i = ZERO_INT; i < next.length && count < size; i++) {

                if (next[i] > ZERO_INT) {
                    next[i]--;
                    count++;
                }
            }
            if (count == size) {

                double price = size * BASE_PRICE * (1 - DISCOUNT.get(size));

                minPrice = Math.min(minPrice, price + findBestPrice(next));
            }

        }
        return minPrice;
    }

    public List<String> getListOfBooks() {
        return Arrays.stream(BookType.values()).map(BookType::getTitle).collect(Collectors.toList());
    }
}
