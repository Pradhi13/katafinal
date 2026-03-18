package com.bnpp.kata.developmentbooks.service;

import org.springframework.stereotype.Service;

import static com.bnpp.kata.developmentbooks.constants.Constants.BASE_PRICE;

@Service
public class BookPriceService {

    public double calculateBookPrice(String book,int quantity){
        return BASE_PRICE * quantity;
    }
}
