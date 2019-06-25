package com.sf9000.anagram.model;

import java.util.HashMap;
import java.util.Map;

public class WordEquivalency {

    private String word;
    private int binary;
    private Map<Character, Integer> countLetter = new HashMap<>();

    public WordEquivalency setWord(String word) {
        this.word = word;
        return this;
    }

    public String getWord() {
        return word;
    }

    public WordEquivalency setBinary(int binary) {
        this.binary = binary;
        return this;
    }

    public int getBinary() {
        return binary;
    }

    public WordEquivalency setCountLetter(Map<Character, Integer> countLetter) {
        this.countLetter = countLetter;
        return this;
    }

    public Map<Character, Integer> getCountLetter() {
        return countLetter;
    }

    public WordEquivalency create(String word) {

        WordEquivalency wordEquivalency = new WordEquivalency().setWord(word);

        int wordInBinary = 0;
        for (char letter : word.toCharArray()) {
            Integer binaryEquivalent = BinaryEquivalency.BINARY_EQUIVALENCY.get(letter);

            if (binaryEquivalent != null) {
                wordInBinary = wordInBinary | binaryEquivalent;

                wordEquivalency.setBinary(wordInBinary);

                Map<Character, Integer> letterCountMap = wordEquivalency.getCountLetter();

                Integer letterCount = letterCountMap.get(letter);
                if (letterCount == null) {
                    letterCountMap.put(letter, 1);
                } else {
                    letterCountMap.put(letter, ++letterCount);
                }
            }
        }

        return wordEquivalency;

    }

}
