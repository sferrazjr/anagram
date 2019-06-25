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

    public DictionaryWord create(String word) {

        DictionaryWord dictionaryWord = new DictionaryWord().setWord(word);

        int wordInBinary = 0;
        for (char letter : word.toCharArray()) {
            Integer binaryEquivalent = BinaryEquivalency.BINARY_EQUIVALENCY.get(letter);

            if (binaryEquivalent != null) {
                wordInBinary = wordInBinary | binaryEquivalent;

                dictionaryWord.setBinary(wordInBinary);

                Map<Character, Integer> letterCountMap = dictionaryWord.getCountLetter();

                Integer letterCount = letterCountMap.get(letter);
                if (letterCount == null) {
                    letterCountMap.put(letter, 1);
                } else {
                    letterCountMap.put(letter, ++letterCount);
                }
            }
        }

        return dictionaryWord;

    }

}
