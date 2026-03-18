package com.bnpp.kata.developmentbooks.controller;

import com.bnpp.kata.developmentbooks.constants.BookType;
import com.bnpp.kata.developmentbooks.model.BookItems;
import com.bnpp.kata.developmentbooks.model.Books;
import com.bnpp.kata.developmentbooks.model.GroupDetails;
import com.bnpp.kata.developmentbooks.model.OrderResponse;
import com.bnpp.kata.developmentbooks.service.DevelopmentBooksService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(DevelopmentBooksController.class)
public class DevelopmentBooksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DevelopmentBooksService developmentBooksService;


    @Test
    @DisplayName("should return list of available books")
    void shouldReturnListOfBooks() throws Exception {
        List<String> mockBooks = Arrays.stream(BookType.values())
                .map(BookType::getTitle)
                .collect(Collectors.toList());
        Mockito.when(developmentBooksService.getListOfBooks()).thenReturn(mockBooks);
        mockMvc.perform(get("/api/v1/books/getListOfBooks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[0]").value("Clean Code"))
                .andExpect(jsonPath("$[1]").value("The Clean Coder"))
                .andExpect(jsonPath("$[2]").value("Clean Architecture"))
                .andExpect(jsonPath("$[3]").value("Test Driven Development by Example"))
                .andExpect(jsonPath("$[4]").value("Working Effectively With Legacy Code"));
    }

    @Test
    @DisplayName("should return development books discounted price")
    void shouldReturnDevelopmentBooksPrice() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Books books = new Books();

        books.setBooks(List.of(
                new BookItems("Clean Code",2),
                new BookItems("Clean Coder",2),
                new BookItems("Clean Architecture",2),
                new BookItems("TDD",1),
                new BookItems("Legacy Code",1)));


        OrderResponse response = getOrderResponse();
        Mockito.when(developmentBooksService.calculateBookPrice(Mockito.anyList()))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/books/calculatePrice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(books)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groups").isArray())
                .andExpect(jsonPath("$.totalPrice").value(400.0))
                .andExpect(jsonPath("$.discountedPrice").value(320.0));
    }

    @Test
    @DisplayName("should return bad request for invalid json")
    void shouldReturnBadRequestForInvalidJson() throws Exception {

        mockMvc.perform(post("/api/v1/books/calculatePrice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("invalid-json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return unsupported media type when content type is missing")
    void shouldReturnUnsupportedMediaTypeWhenContentTypeMissing() throws Exception {

        mockMvc.perform(post("/api/v1/books/calculatePrice")
                        .content("{\"books\":[1,2,3]}"))
                .andExpect(status().isUnsupportedMediaType());
    }

    private static OrderResponse getOrderResponse() {
        List<String> books1 = Arrays.asList("Clean Code","Clean Coder","Clean Architecture","TDD","Legacy Code");
        List<String> books2 = Arrays.asList("Clean Coder","Clean Architecture","TDD","Legacy Code");

        GroupDetails groupDetails1 = new GroupDetails(books1,4, 20.0, 160.0);
        GroupDetails groupDetails2 = new GroupDetails(books2,4, 20.0, 160.0);

        List<GroupDetails> groupDetailsList = new ArrayList<>();
        groupDetailsList.add(groupDetails1);
        groupDetailsList.add(groupDetails2);
        return new OrderResponse(groupDetailsList,
                400.0,320.0
        );
    }

}
