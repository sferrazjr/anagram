package com.sf9000.anagram.service;

import com.sf9000.anagram.repository.DictionaryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnagramSolverServiceTest {

    @InjectMocks
    AnagramSolverService anagramSolverService;

    @Mock
    DictionaryRepository dictionaryRepository;

    List<String> anagramBest;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        anagramBest = new ArrayList<>();
        anagramBest.add("best");
        anagramBest.add("bets");

        Map<String, Integer> dictionary = new HashMap<>();
        dictionary.put("best",786450);
        dictionary.put("bets",786450);

        Mockito.when(dictionaryRepository.getDictionary())
                .thenReturn(dictionary);

        Map<String, Map<Character, Integer>> dictionaryCountLetter = new HashMap<>();
        Map<Character, Integer> bestCountLetters = new HashMap<>();
        bestCountLetters.put('b',1);
        bestCountLetters.put('e',1);
        bestCountLetters.put('s',1);
        bestCountLetters.put('t',1);
        dictionaryCountLetter.put("best", bestCountLetters);
        dictionaryCountLetter.put("bets", bestCountLetters);

        Mockito.when(dictionaryRepository.getDictionaryCountLetter())
                .thenReturn(dictionaryCountLetter);
    }

    @Test
    @DisplayName("Test phrase 'best' should return array with elements best and bets")
    public void testPhraseBestSecret() {

        //GIVEN
        String phrase = "best";

        //WHEN
        List<String> anagramSolution = anagramSolverService.solve(phrase);

        //THEN
        assertEquals(2, anagramSolution.size());
        assertArrayEquals(anagramBest.toArray(), anagramSolution.toArray());

    }

    @Test
    @DisplayName("Test phrase 'b-+est' has special characters should return same as best")
    public void testPhraseITCrowd() {

        //GIVEN
        String phrase = "b-+est";

        //WHEN
        List<String> anagramSolution = anagramSolverService.solve(phrase);

        //THEN
        assertEquals(2, anagramSolution.size());
        assertArrayEquals(anagramBest.toArray(), anagramSolution.toArray());

    }

    @Test
    @DisplayName("Test phrase 'BEst' has upper case characters should return same as best")
    public void testPhraseBestWithUpperCase() {

        //GIVEN
        String phrase = "BEst";

        //WHEN
        List<String> anagramSolution = anagramSolverService.solve(phrase);

        //THEN
        assertEquals(2, anagramSolution.size());
        assertArrayEquals(anagramBest.toArray(), anagramSolution.toArray());

    }

    @Test
    @DisplayName("Test phrase 'BEs*t' has upper case and special characters should return same as best")
    public void testPhraseBestWithUpperCaseAndSpecialCharacter() {

        //GIVEN
        String phrase = "BEs*t";

        //WHEN
        List<String> anagramSolution = anagramSolverService.solve(phrase);

        //THEN
        assertEquals(2, anagramSolution.size());
        assertArrayEquals(anagramBest.toArray(), anagramSolution.toArray());

    }

    @Test
    @DisplayName("Test phrase 'B E st' with space should return same as best")
    public void spacesShouldNotBeConsidered() {

        //GIVEN
        String phrase = "B E st";

        //WHEN
        List<String> anagramSolution = anagramSolverService.solve(phrase);

        //THEN
        assertEquals(2, anagramSolution.size());
        assertArrayEquals(anagramBest.toArray(), anagramSolution.toArray());

    }


    @Test
    @DisplayName("Test phrase IT is smaller than 3 chars should return an array with 0 elements")
    public void testPhraseWithOnlyOneWordSmallerThan3Chars() {
        //GIVEN
        String phraseIT = "IT";

        //WHEN
        List<String> anagramSolution = anagramSolverService.solve(phraseIT);

        //THEN
        assertEquals(0, anagramSolution.size());
    }


}
