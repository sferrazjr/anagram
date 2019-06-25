package com.sf9000.anagram.service;

import com.sf9000.anagram.exception.InvalidInputException;
import com.sf9000.anagram.util.SanitizeUtil;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CounterLettersService {

    public int counterLetters(final String wordToCountLetter) {

        final String word = SanitizeUtil.sanitizePhrase(wordToCountLetter);

        Set<Character> characterList = new HashSet<>();

        for (Character character : word.toCharArray()) {
            characterList.add(character);
        }

        return characterList.size();
    }
}
