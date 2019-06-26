package com.sf9000.anagram.service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CounterLettersServiceTest {

    private CounterLettersService counterLettersService = new CounterLettersService();

    @Test
    public void countLetter(){
        assertEquals(3, counterLettersService.counterLetters("maja"));
        assertEquals(4, counterLettersService.counterLetters("maria"));
        assertEquals(4, counterLettersService.counterLetters("lima"));
        assertEquals(5, counterLettersService.counterLetters("limas"));
        assertEquals(6, counterLettersService.counterLetters("sergio"));
        assertEquals(6, counterLettersService.counterLetters("bestsecret"));
    }


}
