package com.bnpp.kata.developmentbooks.service;

import com.bnpp.kata.developmentbooks.constants.BookType;
import com.bnpp.kata.developmentbooks.exception.InvalidBookException;
import com.bnpp.kata.developmentbooks.model.BookItems;
import com.bnpp.kata.developmentbooks.model.GroupDetails;
import com.bnpp.kata.developmentbooks.model.OrderResponse;
import com.bnpp.kata.developmentbooks.model.PriceResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.bnpp.kata.developmentbooks.constants.Constants.*;

@Service
public class DevelopmentBooksService {

    public OrderResponse calculateBookPrice(@NotEmpty(message = "Book list cannot be empty")
                                            List<@Valid BookItems> bookItemsList) {

        if (bookItemsList.stream().anyMatch(item ->
                item.getQuantity() <= ZERO_INT || item.getTitle().trim().isEmpty())) {
            throw new InvalidBookException("Invalid book items: check quantity/title");
        }
        int[] quantities = bookItemsList.stream()
                .mapToInt(BookItems::getQuantity)
                .toArray();

        String[] titles = bookItemsList.stream()
                .map(BookItems::getTitle)
                .toArray(String[]::new);

        PriceResult result = findBestPrice(quantities, titles);

        int totalBooks = Arrays.stream(quantities).sum();

        double totalPrice = totalBooks * BASE_PRICE;

        return new OrderResponse(
                result.getGroups(),
                totalPrice,
                result.getPrice()
        );
    }

    private PriceResult findBestPrice(int[] quantities, String[] titles) {

        if (Arrays.stream(quantities).allMatch(quantityValue -> quantityValue == ZERO_INT)) {
            return new PriceResult(ZERO_DOUBLE, new ArrayList<>());
        }

        return IntStream.rangeClosed(ONE, quantities.length)
                .mapToObj(size -> calculateGroupPrice(size, quantities, titles))
                .filter(Objects::nonNull)
                .min(Comparator.comparingDouble(PriceResult::getPrice))
                .orElse(new PriceResult(Double.MAX_VALUE, new ArrayList<>()));
    }

    private PriceResult calculateGroupPrice(int size, int[] quantities, String[] titles) {

        int[] next = Arrays.copyOf(quantities, quantities.length);

        List<Integer> selectedIndices = IntStream.range(ZERO_INT, next.length)
                .filter(index -> next[index] > ZERO_INT)
                .limit(size)
                .boxed()
                .toList();
        if (selectedIndices.size() != size) {
            return null;
        }
        selectedIndices.forEach(index -> next[index]--);

        List<String> groupBooks = selectedIndices.stream().map(index -> titles[index]).toList();

        double groupPrice = size * BASE_PRICE * (ONE - DISCOUNT.get(size));

        PriceResult result = findBestPrice(next, titles);

        double total = groupPrice + result.getPrice();

        List<GroupDetails> groups = new ArrayList<>();

        groups.add(new GroupDetails(groupBooks, size, DISCOUNT.get(size) * 100, groupPrice));

        groups.addAll(result.getGroups());

        return new PriceResult(total, groups);
    }

    public List<String> getListOfBooks() {
        return Arrays.stream(BookType.values()).map(BookType::getTitle).collect(Collectors.toList());
    }
}
