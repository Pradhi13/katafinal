package com.bnpp.kata.developmentbooks.exception;

public class InvalidBookException extends IllegalArgumentException {
    public InvalidBookException(String message) {
        super(message);
    }
}
