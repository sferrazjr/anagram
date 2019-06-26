package com.sf9000.anagram.domain;

public class AnagramApiError {

    private String message;

    public AnagramApiError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
