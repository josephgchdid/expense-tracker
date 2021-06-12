package com.example.expense.model;

import org.springframework.http.HttpStatus;

public class ErrorMessage {

    private String errorMessage;

    private HttpStatus httpStatus;

    public ErrorMessage(String errorMessage, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }


}
