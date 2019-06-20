package com.sf9000.anagram.model;

import java.util.HashMap;
import java.util.Map;

public class DictionaryWord {

    private String word;
    private int binary;
    private Map<Character, Integer> countLetter = new HashMap<>();

    public DictionaryWord setWord(String word) {
        this.word = word;
        return this;
    }

    public String getWord() {
        return word;
    }

    public DictionaryWord setBinary(int binary) {
        this.binary = binary;
        return this;
    }

    public int getBinary() {
        return binary;
    }

    public DictionaryWord setCountLetter(Map<Character, Integer> countLetter) {
        this.countLetter = countLetter;
        return this;
    }

    public Map<Character, Integer> getCountLetter() {
        return countLetter;
    }
}
