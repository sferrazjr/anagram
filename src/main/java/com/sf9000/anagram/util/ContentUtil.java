package com.sf9000.anagram.util;

import com.sf9000.anagram.exception.InvalidInputException;
import org.springframework.web.bind.annotation.PathVariable;

public class ContentUtil {

    public static void wordInputValidation(@PathVariable String word) throws InvalidInputException {
        if (word == null || word.isEmpty() || word.length() < 3) {
            throw new InvalidInputException("Word should contain at least 3 characters");
        }
    }

}
