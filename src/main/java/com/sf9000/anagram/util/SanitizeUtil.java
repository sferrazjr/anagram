package com.sf9000.anagram.util;

public class SanitizeUtil {

    public static String sanitizePhrase(String phraseToSolve) {
        return phraseToSolve.toLowerCase().replaceAll("[^a-z]", "");
    }

}
