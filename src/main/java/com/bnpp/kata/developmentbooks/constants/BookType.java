package com.bnpp.kata.developmentbooks.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BookType {

    CLEAN_CODE("Clean Code"),
    CLEAN_CODER("The Clean Coder"),
    CLEAN_ARCHITECTURE("Clean Architecture"),
    TDD_BY_EXAMPLE("Test Driven Development by Example"),
    LEGACY_CODE("Working Effectively With Legacy Code");
    private final String title;
}
