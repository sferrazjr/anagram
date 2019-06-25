package com.sf9000.anagram.service;

import com.sf9000.anagram.model.WordEquivalency;
import com.sf9000.anagram.repository.DictionaryRepository;
import com.sf9000.anagram.util.SanitizeUtil;
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

        final WordEquivalency phraseWordEquivalency = new WordEquivalency().create(phrase);

        final Map<String, WordEquivalency> dictionaryWordMap = dictionaryRepository.getDictionaryWordMap();

        List<String> anagramWordListOrdered = createAnagramListOfWords(dictionaryWordMap, phraseWordEquivalency);

        List<String> anagramList = new ArrayList<>();

        findAnagram(anagramWordListOrdered, anagramList, 0, "", dictionaryWordMap, phrase);

        return anagramList;

    }


    /**
     * Creates an alphabetical ordered list of all strings that are anagrams of phrase and are in the dictionary
     * @param dictionaryWordMap
     * @param phraseWordEquivalency
     * @return
     */
    private List<String> createAnagramListOfWords(Map<String, WordEquivalency> dictionaryWordMap, WordEquivalency phraseWordEquivalency) {
        List<String> anagramWordList = new ArrayList<>();

        for (Map.Entry<String, WordEquivalency> stringDictionaryWordEntry : dictionaryWordMap.entrySet()) {

            WordEquivalency wordEquivalency = stringDictionaryWordEntry.getValue();

            int phraseAndWordInBinary = phraseWordEquivalency.getBinary() | wordEquivalency.getBinary();

            if ((phraseAndWordInBinary ^ phraseWordEquivalency.getBinary()) == 0) {

                boolean isNumberOfLettersValid = isNumberOfPhraseLetterSmallerThanDictionaryWordLetters(phraseWordEquivalency, wordEquivalency);

                if (isNumberOfLettersValid) {
                    anagramWordList.add(wordEquivalency.getWord());
                }
            }
        }

        return anagramWordList.stream().sorted().collect(Collectors.toList());
    }

    private boolean isNumberOfPhraseLetterSmallerThanDictionaryWordLetters(
            WordEquivalency phraseEquivalency,
            WordEquivalency wordEquivalency) {

        Map<Character, Integer> dictionaryWordCountLetterMap = wordEquivalency.getCountLetter();

        for (Map.Entry<Character, Integer> characterIntegerEntry : dictionaryWordCountLetterMap.entrySet()) {

            Character dictionaryLetter = characterIntegerEntry.getKey();

            Integer letterCounter = phraseEquivalency.getCountLetter().get(dictionaryLetter);

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
            String returnPhrase,
            Map<String, WordEquivalency> dictionaryWordMap,
            String phrase) {

        for (int i = starterLoop; i < anagramWordListOrdered.size(); i++) {

            String anagramWord = anagramWordListOrdered.get(i);

            boolean isValidWord = true;

            String possibleReturnPhrase = returnPhrase;

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
