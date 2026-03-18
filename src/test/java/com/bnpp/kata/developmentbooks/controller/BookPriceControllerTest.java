package com.bnpp.kata.developmentbooks.controller;

import com.bnpp.kata.developmentbooks.constants.BookType;
import com.bnpp.kata.developmentbooks.service.BookPriceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest(BookPriceController.class)
public class BookPriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookPriceService bookPricingService;

    @Test
    @DisplayName("should return list of available books")
    void shouldReturnListOfBooks() throws Exception {
        List<String> mockBooks = Arrays.stream(BookType.values())
                .map(BookType::getTitle)
                .collect(Collectors.toList());
        Mockito.when(bookPricingService.getListOfBooks()).thenReturn(mockBooks);
        mockMvc.perform(get("/api/v1/books/getListOfBooks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[0]").value("Clean Code"))
                .andExpect(jsonPath("$[1]").value("The Clean Coder"))
                .andExpect(jsonPath("$[2]").value("Clean Architecture"))
                .andExpect(jsonPath("$[3]").value("Test Driven Development by Example"))
                .andExpect(jsonPath("$[4]").value("Working Effectively With Legacy Code"));
    }

}
