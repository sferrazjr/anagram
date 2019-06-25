package com.sf9000.anagram.service;

import com.sf9000.anagram.util.SanitizeUtil;
import com.sf9000.anagram.model.BinaryEquivalency;
import com.sf9000.anagram.model.DictionaryWord;
import com.sf9000.anagram.repository.DictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AnagramSolverService {

    @Autowired
    DictionaryRepository dictionaryRepository;

    public List<String> solve(final String phraseToSolve) {

        final String phrase = SanitizeUtil.sanitizePhrase(phraseToSolve);

        Map<String, DictionaryWord> dictionaryWordMap = dictionaryRepository.getDictionaryWordMap();

        int phraseInBinary = getPhraseInBinary(phrase);

        Map<Character, Integer> phraseCountLetterMap = countLetterOfPhrase(phrase.toCharArray());

        List<String> anagramWordList = new ArrayList<>();

        for (Map.Entry<String, DictionaryWord> stringDictionaryWordEntry : dictionaryWordMap.entrySet()) {

            DictionaryWord dictionaryWord = stringDictionaryWordEntry.getValue();

            int phraseAndWordInBinary = phraseInBinary | dictionaryWord.getBinary();

            if ((phraseAndWordInBinary ^ phraseInBinary) == 0) {

                boolean isNumberOfLettersValid = isNumberOfPhraseLetterSmallerThanDictionaryWordLetters(phraseCountLetterMap, dictionaryWord);

                if (isNumberOfLettersValid) {
                    anagramWordList.add(dictionaryWord.getWord());
                }
            }
        }

        List<String> anagramWordListOrdered = anagramWordList.stream().sorted().collect(Collectors.toList());

        List<String> anagramList = new ArrayList<>();

        findAnagram(anagramWordListOrdered, anagramList, 0, "", dictionaryWordMap, phrase);

        return anagramList;

    }

    private int getPhraseInBinary(String phrase) {
        int phraseInBinary = 0;

        for (char letter : phrase.toCharArray()) {
            Integer binaryEquivalent = BinaryEquivalency.BINARY_EQUIVALENCY.get(letter);
            phraseInBinary = phraseInBinary | binaryEquivalent;
        }
        return phraseInBinary;
    }

    private boolean isNumberOfPhraseLetterSmallerThanDictionaryWordLetters(
            Map<Character, Integer> phraseCountLetterMap,
            DictionaryWord dictionaryWord) {

        Map<Character, Integer> dictionaryWordCountLetterMap = dictionaryWord.getCountLetter();

        for (Map.Entry<Character, Integer> characterIntegerEntry : dictionaryWordCountLetterMap.entrySet()) {

            Character dictionaryLetter = characterIntegerEntry.getKey();

            Integer letterCounter = phraseCountLetterMap.get(dictionaryLetter);

            if (letterCounter != null && characterIntegerEntry.getValue() > letterCounter) {
                return false;
            }

        }
        return true;
    }

    /**
     * Count number of letter and how many times it repeats in the word.<BR>
     * Example: word maria returns and Map as {a=2, r=1, i=1, m=1}
     * @param phraseCharArray
     * @return java.util.Map<Character, Integer>
     */
    private Map<Character, Integer> countLetterOfPhrase(char[] phraseCharArray) {
        Map<Character, Integer> phraseCountMap = new HashMap<>();

        for (char letter : phraseCharArray) {
            Integer letterCount = phraseCountMap.get(letter);
            if (letterCount == null) {
                phraseCountMap.put(letter, 1);
            } else {
                phraseCountMap.put(letter, ++letterCount);
            }

        }
        return phraseCountMap;
    }

    private void findAnagram(
            List<String> anagramWordListOrdered,
            List<String> anagramList,
            Integer starterLoop,
            String myReturnPhrase,
            Map<String, DictionaryWord> dictionaryWordMap,
            String phrase) {

        for (int i = starterLoop; i < anagramWordListOrdered.size(); i++) {

            String anagramWord = anagramWordListOrdered.get(i);

            boolean isValidWord = true;

            String possibleReturnPhrase = myReturnPhrase;

            Map<Character, Integer> anagramWordCountLetterMap = dictionaryWordMap.get(anagramWord).getCountLetter();

            Map<Character, Integer> phraseCountLetterMap = countLetterOfPhrase(phrase.toCharArray());

            for (Map.Entry<Character, Integer> entry : anagramWordCountLetterMap.entrySet()) {

                Character anagramWordLetter = entry.getKey();

                Integer anagramLetterCounter = phraseCountLetterMap.get(anagramWordLetter);

                if (entry.getValue() > anagramLetterCounter) {
                    isValidWord = false;
                }

            }

            if (isValidWord) {

                possibleReturnPhrase = possibleReturnPhrase + " " + anagramWord;

                char[] possibleReturnPhraseCharArray = possibleReturnPhrase.replaceAll(" ", "").toCharArray();

                for (char possibleReturnPhraseLetter : possibleReturnPhraseCharArray) {

                    Integer myInteger = phraseCountLetterMap.get(possibleReturnPhraseLetter);
                    if (myInteger != null) {
                        phraseCountLetterMap.put(possibleReturnPhraseLetter, --myInteger);
                    }
                }
            }

            boolean isValidPhrase = true;

            for (Map.Entry<Character, Integer> phraseCountEntry : phraseCountLetterMap.entrySet()) {
                if (phraseCountEntry.getValue()!=null && phraseCountEntry.getValue() > 0) {
                    isValidPhrase = false;
                }
            }

            int lengthOfReturnPhraseWithOutSpaces = possibleReturnPhrase.replaceAll(" ", "").length();
            int lengthOfMyPhraseWithOutSpaces = phrase.replaceAll(" ", "").length();

            if (lengthOfReturnPhraseWithOutSpaces < lengthOfMyPhraseWithOutSpaces - 2) {
                findAnagram(anagramWordListOrdered, anagramList, i + 1, possibleReturnPhrase, dictionaryWordMap, phrase);
            }

            if (isValidPhrase && (lengthOfReturnPhraseWithOutSpaces == lengthOfMyPhraseWithOutSpaces)) {
                anagramList.add(possibleReturnPhrase.trim());
            }
        }
    }
}
