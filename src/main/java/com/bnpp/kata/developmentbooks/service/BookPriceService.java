package com.bnpp.kata.developmentbooks.service;

import org.springframework.stereotype.Service;

@Service
public class BookPriceService {

    public double calculateBookPrice(String book,int quantity){
        return 50.0 * quantity;
    }
}
