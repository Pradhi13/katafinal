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

        final Map<String, PriceResult> cache = new HashMap<>();

        if (bookItemsList.stream().anyMatch(item ->
                item.getQuantity() <= ZERO_INT || item.getTitle().trim().isEmpty())) {
            throw new InvalidBookException("Invalid book items: check quantity/title");
        }
        int[] quantities = getBookQuantities(bookItemsList);

        PriceResult result = findBestPrice(quantities, getBookTitles(bookItemsList),cache);

        int totalBooks = Arrays.stream(quantities).sum();

        double totalPrice = totalBooks * BASE_PRICE;

        return OrderResponse.builder()
                .groups(result.getGroups())
                .totalPrice(totalPrice)
                .discountedPrice(result.getPrice())
                .build();
    }

    private PriceResult findBestPrice(final int[] quantities, final String[] titles, Map<String,PriceResult> cache) {

        String key = Arrays.toString(quantities);

        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        if (Arrays.stream(quantities).allMatch(quantityValue -> quantityValue == ZERO_INT)) {
            PriceResult result = PriceResult.builder().price(ZERO_DOUBLE).groups(new ArrayList<>()).build();
            cache.put(key,result);
            return result;
        }

        PriceResult bestResult = IntStream.rangeClosed(1, quantities.length)
                .mapToObj(size -> calculateGroupPrice(size, quantities, titles, cache))
                .filter(Objects::nonNull)
                .min(Comparator.comparingDouble(PriceResult::getPrice))
                .map(best -> PriceResult.builder()
                        .price(best.getPrice())
                        .groups(best.getGroups())
                        .build())
                .orElse(PriceResult.builder()
                        .price(Double.MAX_VALUE)
                        .groups(new ArrayList<>())
                        .build());
        cache.put(key,bestResult);
        return bestResult;
    }

    private PriceResult calculateGroupPrice(final int size, final int[] quantities, final String[] titles, Map<String,PriceResult> cache) {

        int[] tmpArray = Arrays.copyOf(quantities, quantities.length);

        List<Integer> selectedIndices = IntStream.range(ZERO_INT, tmpArray.length)
                .filter(index -> tmpArray[index] > ZERO_INT)
                .limit(size)
                .boxed()
                .toList();
        if (selectedIndices.size() != size) {
            return null;
        }
        selectedIndices.forEach(index -> tmpArray[index]--);

        List<String> groupBooks = selectedIndices.stream().map(index -> titles[index]).toList();

        double groupPrice = size * BASE_PRICE * (ONE - DISCOUNT.get(size));

        PriceResult result = findBestPrice(tmpArray, titles, cache);

        double total = groupPrice + result.getPrice();

        GroupDetails group = GroupDetails.builder()
                .books(groupBooks)
                .groupSize(size)
                .discountPercentage(DISCOUNT.get(size) * HUNDRED)
                .afterdiscountPrice(groupPrice)
                .build();

        PriceResult recursive = findBestPrice(tmpArray, titles, cache);

        return PriceResult.builder()
                .price(groupPrice + recursive.getPrice())
                .groups(concatenate(group, recursive.getGroups()))
                .build();
    }

    public List<String> getListOfBooks() {
        return Arrays.stream(BookType.values()).map(BookType::getTitle).collect(Collectors.toList());
    }

    private String[] getBookTitles(List<BookItems> bookItemsList) {
        return bookItemsList.stream()
                .map(BookItems::getTitle)
                .toArray(String[]::new);
    }
    private int[] getBookQuantities(List<BookItems> bookItemsList) {
        return bookItemsList.stream()
                .mapToInt(BookItems::getQuantity)
                .toArray();
    }
    private List<GroupDetails> concatenate(GroupDetails newGroup, List<GroupDetails> existing) {
        List<GroupDetails> all = new ArrayList<>(existing);
        all.add(ZERO_INT, newGroup);
        return all;
    }
}
