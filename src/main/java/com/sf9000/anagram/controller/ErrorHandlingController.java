package com.sf9000.anagram.controller;

import com.sf9000.anagram.domain.AnagramApiError;
import com.sf9000.anagram.exception.InvalidInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandlingController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AnagramApiError> generalException(Exception exception) {

        return new ResponseEntity<>(
                new AnagramApiError(exception.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<AnagramApiError> invalidWordException(InvalidInputException invalidInputException) {

        return new ResponseEntity<>(
                new AnagramApiError(invalidInputException.getMessage()),
                HttpStatus.BAD_REQUEST);

    }


}
