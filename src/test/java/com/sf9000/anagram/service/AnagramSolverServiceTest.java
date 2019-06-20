package com.sf9000.anagram.service;

import com.sf9000.anagram.model.DictionaryWord;
import com.sf9000.anagram.repository.DictionaryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AnagramSolverServiceTest {

    @InjectMocks
    AnagramSolverService anagramSolverService;

    @Mock
    DictionaryRepository dictionaryRepository;

    Map<String, DictionaryWord> dictionaryWordMap;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        Map<Character, Integer> bestCountLetters = new HashMap<>();
        bestCountLetters.put('b',1);
        bestCountLetters.put('e',1);
        bestCountLetters.put('s',1);
        bestCountLetters.put('t',1);

        dictionaryWordMap = new HashMap<>();

        DictionaryWord dictionaryWordBest = new DictionaryWord()
                .setWord("best")
                .setBinary(786450)
                .setCountLetter(bestCountLetters);

        DictionaryWord dictionaryWordBets = new DictionaryWord()
                .setWord("bets")
                .setBinary(786450)
                .setCountLetter(bestCountLetters);

        dictionaryWordMap.put("best", dictionaryWordBest);
        dictionaryWordMap.put("bets", dictionaryWordBets);

        Mockito.when(dictionaryRepository.getDictionaryWordMap()).thenReturn(dictionaryWordMap);

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
        assertNotNull(dictionaryWordMap.get(anagramSolution.get(0)));
        assertNotNull(dictionaryWordMap.get(anagramSolution.get(1)));

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
        assertNotNull(dictionaryWordMap.get(anagramSolution.get(0)));
        assertNotNull(dictionaryWordMap.get(anagramSolution.get(1)));

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
        assertNotNull(dictionaryWordMap.get(anagramSolution.get(0)));
        assertNotNull(dictionaryWordMap.get(anagramSolution.get(1)));

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
        assertNotNull(dictionaryWordMap.get(anagramSolution.get(0)));
        assertNotNull(dictionaryWordMap.get(anagramSolution.get(1)));

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
        assertNotNull(dictionaryWordMap.get(anagramSolution.get(0)));
        assertNotNull(dictionaryWordMap.get(anagramSolution.get(1)));

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
