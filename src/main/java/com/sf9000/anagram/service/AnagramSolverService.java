package com.sf9000.anagram.service;

import com.sf9000.anagram.model.BinaryEquivalency;
import com.sf9000.anagram.repository.DictionaryRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AnagramSolverService {

    private DictionaryRepository dictionaryRepository = DictionaryRepository.getInstance();

    public List<String> solve(final String phraseToSolve) {

        final String phrase = sanitizePhrase(phraseToSolve);

        Map<String, Integer> dictionary = dictionaryRepository.getDictionary();

        Map<String, Map<Character, Integer>> dictionaryCountLetter = dictionaryRepository.getDictionaryCountLetter();

        int phraseInBinary = 0;

        for (char letter : phrase.toCharArray()) {
            Integer binaryEquivalent = BinaryEquivalency.BINARY_EQUIVALENCY.get(letter);
            phraseInBinary = phraseInBinary | binaryEquivalent;
        }

        List<String> anagramWordList = new ArrayList<>();

        Map<Character, Integer> phraseCountLetterMap = countLetterOfPhrase(phrase.toCharArray());

        for (Map.Entry<String, Integer> dictionaryWord : dictionary.entrySet()) {

            int phraseAndWordInBinary = phraseInBinary | dictionaryWord.getValue();

            if ((phraseAndWordInBinary ^ phraseInBinary) == 0) {

                Map<Character, Integer> dictionaryWordCountLetterMap = dictionaryCountLetter.get(dictionaryWord.getKey());

                boolean isValidWord = true;

                for (Map.Entry<Character, Integer> entry : dictionaryWordCountLetterMap.entrySet()) {

                    Character dictionaryLetter = entry.getKey();

                    Integer letterCounter = phraseCountLetterMap.get(dictionaryLetter);

                    if (letterCounter!= null && entry.getValue() > letterCounter) {
                        isValidWord = false;
                    }

                }

                if(isValidWord){
                    anagramWordList.add(dictionaryWord.getKey());
                }
            }
        }

        List<String> anagramWordListOrdered = anagramWordList.stream().sorted().collect(Collectors.toList());

        List<String> anagramList = new ArrayList<>();

        findAnagram(anagramWordListOrdered, anagramList, 0, "", dictionaryCountLetter, phrase);

        return anagramList;

    }

    private String sanitizePhrase(String phraseToSolve) {
        return phraseToSolve.toLowerCase().replaceAll("[^a-z]", "");
    }

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
            Map<String, Map<Character, Integer>> dictionaryCountLetter,
            String phrase) {

        for (int i = starterLoop; i < anagramWordListOrdered.size(); i++) {

            String anagramWord = anagramWordListOrdered.get(i);

            boolean isValidWord = true;

            String possibleReturnPhrase = myReturnPhrase;

            Map<Character, Integer> anagramWordCountLetterMap = dictionaryCountLetter.get(anagramWord);

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

                char[] possibleReturnPhraseCharArray = possibleReturnPhrase.replaceAll(" ","").toCharArray();

                for (char possibleReturnPhraseLetter : possibleReturnPhraseCharArray) {

                    Integer myInteger = phraseCountLetterMap.get(possibleReturnPhraseLetter);
                    if (myInteger != null) {
                        phraseCountLetterMap.put(possibleReturnPhraseLetter, --myInteger);
                    }
                }
            }

            boolean isValidPhrase = true;

            for (Map.Entry<Character, Integer> phraseCountEntry : phraseCountLetterMap.entrySet()) {
                Integer myInteger = phraseCountEntry.getValue();
                if (myInteger > 0) {
                    isValidPhrase = false;
                }
            }

            int lengthOfReturnPhraseWithOutSpaces = possibleReturnPhrase.replaceAll(" ", "").length();
            int lengthOfMyPhraseWithOutSpaces = phrase.replaceAll(" ", "").length();

            if(lengthOfReturnPhraseWithOutSpaces < lengthOfMyPhraseWithOutSpaces-2) {
                findAnagram(anagramWordListOrdered, anagramList, i + 1, possibleReturnPhrase, dictionaryCountLetter, phrase);
            }

            if (isValidPhrase && (lengthOfReturnPhraseWithOutSpaces == lengthOfMyPhraseWithOutSpaces)) {
                anagramList.add(possibleReturnPhrase.trim());
            }
        }
    }
}
