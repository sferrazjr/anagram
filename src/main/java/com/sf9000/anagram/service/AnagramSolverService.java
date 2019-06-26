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

        findAnagram(anagramWordListOrdered, anagramList, 0, "", dictionaryWordMap, phraseWordEquivalency);

        return anagramList;

    }


    /**
     * Creates an alphabetical ordered list of all strings can be anagrams of the phrase and are in the dictionary
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

    /**
     * Uses the anagramWordListOrdered to create a list of phrases that are anagram of phraseToSolve.<BR>
     *     Recursively gets the returnPhrase to compare with next word of anagramWordListOrdered.
     * @param anagramWordListOrdered
     * @param anagramList
     * @param starterLoop
     * @param returnPhrase
     * @param dictionaryWordMap
     * @param phraseWordEquivalency
     */
    private void findAnagram(
            List<String> anagramWordListOrdered,
            List<String> anagramList,
            Integer starterLoop,
            String returnPhrase,
            Map<String, WordEquivalency> dictionaryWordMap,
            WordEquivalency phraseWordEquivalency) {

        for (int i = starterLoop; i < anagramWordListOrdered.size(); i++) {

            String anagramWord = anagramWordListOrdered.get(i);

            boolean isNumberOfLettersValid = isNumberOfPhraseLetterSmallerThanDictionaryWordLetters(phraseWordEquivalency, dictionaryWordMap.get(anagramWord));

            String possibleReturnPhrase = returnPhrase;

            Map<Character, Integer> phraseCountLetterMap = countLetterOfPhrase(phraseWordEquivalency.getWord().toCharArray());

            if (isNumberOfLettersValid) {

                possibleReturnPhrase = addsCurrentAnagramToPossibleReturnPhrase(anagramWord, possibleReturnPhrase, phraseCountLetterMap);

            }

            boolean isValidPhrase = true;

            for (Map.Entry<Character, Integer> phraseCountEntry : phraseCountLetterMap.entrySet()) {
                if (phraseCountEntry.getValue()!=null && phraseCountEntry.getValue() > 0) {
                    isValidPhrase = false;
                }
            }

            int lengthOfPossibleReturnPhraseWithOutSpaces = possibleReturnPhrase.replaceAll(" ", "").length();
            int lengthOfPhraseWithOutSpaces = phraseWordEquivalency.getWord().replaceAll(" ", "").length();

            if (lengthOfPossibleReturnPhraseWithOutSpaces < lengthOfPhraseWithOutSpaces - 2) {
                findAnagram(anagramWordListOrdered, anagramList, i + 1, possibleReturnPhrase, dictionaryWordMap, phraseWordEquivalency);
            }

            if (isValidPhrase && (lengthOfPossibleReturnPhraseWithOutSpaces == lengthOfPhraseWithOutSpaces)) {
                anagramList.add(possibleReturnPhrase.trim());
            }
        }
    }

    private String addsCurrentAnagramToPossibleReturnPhrase(String anagramWord, String possibleReturnPhrase, Map<Character, Integer> phraseCountLetterMap) {
        possibleReturnPhrase = possibleReturnPhrase + " " + anagramWord;

        char[] possibleReturnPhraseCharArray = possibleReturnPhrase.replaceAll(" ", "").toCharArray();

        for (char possibleReturnPhraseLetter : possibleReturnPhraseCharArray) {

            Integer letterCount = phraseCountLetterMap.get(possibleReturnPhraseLetter);
            if (letterCount != null) {
                phraseCountLetterMap.put(possibleReturnPhraseLetter, --letterCount);
            }
        }
        return possibleReturnPhrase;
    }
}
