package com.sf9000.anagram.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;
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
    @DisplayName("Test phrase 'Best Secret'")
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
    @DisplayName("Test phrase 'IT-Crowd' with special character")
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
    @DisplayName("Test phrase 'Aschheim'")
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
    @DisplayName("Test phrase with words smaller than 3 characters")
    public void testPhraseWithWordsSmallerThan3Chars(){
        fail("not implemented yet");
    }

    @Test
    @DisplayName("Test phrase with only one word smaller than 3 characters")
    public void testPhraseWithOnlyOneWordSmallerThan3Chars(){
        fail("not implemented yet");
    }

    @Test
    @DisplayName("Test phrase with more than 2 words")
    public void testPhraseWithMoreThan2Words(){
        fail("not implemented yet");
    }

}
