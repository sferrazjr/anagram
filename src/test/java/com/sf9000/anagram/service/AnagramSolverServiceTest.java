package com.sf9000.anagram.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnagramSolverServiceTest {

    @InjectMocks
    AnagramSolverService anagramSolverService;

    List<String> anagramBestSecret;
    List<String> anagramIT_Crowd;
    List<String> anagramAschheim;

    {
        anagramBestSecret = new ArrayList<>();
        anagramBestSecret.add("beet crests");
        anagramBestSecret.add("beets crest");
        anagramBestSecret.add("beret sects");
        anagramBestSecret.add("berets sect");
        anagramBestSecret.add("beset crest");
        anagramBestSecret.add("best erects");
        anagramBestSecret.add("best secret");
        anagramBestSecret.add("bests crete");
        anagramBestSecret.add("bests erect");
        anagramBestSecret.add("bet erst sec");
        anagramBestSecret.add("bet rest sec");
        anagramBestSecret.add("bet secrets");
        anagramBestSecret.add("bets erects");
        anagramBestSecret.add("bets secret");
        anagramBestSecret.add("better cess");
        anagramBestSecret.add("betters sec");

        anagramIT_Crowd = new ArrayList<>();
        anagramIT_Crowd.add("cod writ");
        anagramIT_Crowd.add("cord wit");
        anagramIT_Crowd.add("cow dirt");
        anagramIT_Crowd.add("doc writ");
        anagramIT_Crowd.add("tic word");

        anagramAschheim = new ArrayList<>();
        anagramAschheim.add("aches him");
        anagramAschheim.add("ash chime");
        anagramAschheim.add("chase him");
        anagramAschheim.add("chime has");
        anagramAschheim.add("hash mice");
        anagramAschheim.add("hic shame");
        anagramAschheim.add("mice shah");

    }


    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Test phrase 'Best Secret' should return array with 16 elements")
    public void testPhraseBestSecret() throws FileNotFoundException {

        //GIVEN
        String phraseBestSecret = "Best Secret";

        //WHEN
        List<String> anagramSolution = anagramSolverService.solve(phraseBestSecret);

        //THEN
        assertEquals(16, anagramSolution.size());
        assertArrayEquals(anagramBestSecret.toArray(), anagramSolution.toArray());

    }

    @Test
    @DisplayName("Test phrase 'IT-Crowd' has special character should return array with 5 elements")
    public void testPhraseITCrowd() throws FileNotFoundException {

        //GIVEN
        String phraseIT_Crowd = "IT-Crowd";

        //WHEN
        List<String> anagramSolution = anagramSolverService.solve(phraseIT_Crowd);

        //THEN
        assertEquals(5, anagramSolution.size());
        assertArrayEquals(anagramIT_Crowd.toArray(), anagramSolution.toArray());

    }

    @Test
    @DisplayName("Test phrase 'Aschheim' should return an array with 7 elements")
    public void testPhraseAschheim() throws FileNotFoundException {

        //GIVEN
        String phraseAschheim = "Aschheim";

        //WHEN
        List<String> anagramSolution = anagramSolverService.solve(phraseAschheim);

        //THEN
        assertEquals(7, anagramSolution.size());
        assertArrayEquals(anagramAschheim.toArray(), anagramSolution.toArray());
    }

    @Test
    @DisplayName("Test phrase 'IT Crowd' has word smaller than 3 chars should return array with 5 elements")
    public void testPhraseWithWordsSmallerThan3Chars() throws FileNotFoundException {
        //GIVEN
        String phraseIT_Crowd = "IT Crowd";

        //WHEN
        List<String> anagramSolution = anagramSolverService.solve(phraseIT_Crowd);

        //THEN
        assertEquals(5, anagramSolution.size());
        assertArrayEquals(anagramIT_Crowd.toArray(), anagramSolution.toArray());
    }

    @Test
    @DisplayName("Test phrase IT is smaller than 3 chars should return an array with 0 elements")
    public void testPhraseWithOnlyOneWordSmallerThan3Chars() throws FileNotFoundException {
        //GIVEN
        String phraseIT = "IT";

        //WHEN
        List<String> anagramSolution = anagramSolverService.solve(phraseIT);

        //THEN
        assertEquals(0, anagramSolution.size());
    }

    @Test
    @DisplayName("Test phrase 'Best Sec ret' has more than 2 words should return an array with 16 elements")
    public void testPhraseWithMoreThan2Words() throws FileNotFoundException {
        //GIVEN
        String phraseBestSecret = "Best Sec ret";

        //WHEN
        List<String> anagramSolution = anagramSolverService.solve(phraseBestSecret);

        //THEN
        assertEquals(16, anagramSolution.size());
        assertArrayEquals(anagramBestSecret.toArray(), anagramSolution.toArray());
    }

}
