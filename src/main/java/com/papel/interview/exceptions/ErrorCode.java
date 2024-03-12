package com.papel.interview.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INT_0000("Generic server error."),
    INT_0001("Country not found for specified id!"),
    INT_0002("City not found for specified id!"),
    INT_0003("Object has already been modified by another user. Please retry later."),
    INT_0004("Request validation failed.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
