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

    public List<String> solve(String myPhrase) {

        //sanitize myPhrase
        myPhrase = myPhrase.toLowerCase().replaceAll("[^a-z]", "");

        Map<String, Integer> dictionary = dictionaryRepository.getDictionary();

        Map<String, Map<String, Integer>> dictionaryCountLetter = dictionaryRepository.getDictionaryCountLetter();

        int phraseInBinary = 0;

        for (char letter : myPhrase.toCharArray()) {
            Integer binaryEquivalent = BinaryEquivalency.BINARY_EQUIVALENCY.get(letter);
            phraseInBinary = phraseInBinary | binaryEquivalent;
        }

        List<String> myAnagramList = new ArrayList<>();

        Map<String, Integer> myPhraseCountHashMap = myPhraseHashMap(myPhrase.toCharArray());

        for (Map.Entry<String, Integer> dictionaryWord : dictionary.entrySet()) {

            int myAnd = phraseInBinary | dictionaryWord.getValue();

            if ((myAnd ^ phraseInBinary) == 0) {

                Map<String, Integer> stringIntegerMap = dictionaryCountLetter.get(dictionaryWord.getKey());

                boolean isValidWord = true;

                for (Map.Entry<String, Integer> entry : stringIntegerMap.entrySet()) {

                    String myLetter = entry.getKey();

                    Integer myInteger = myPhraseCountHashMap.get(myLetter);

                    if (myInteger!= null && entry.getValue() > myInteger) {
                        isValidWord = false;
                    }

                }

                if(isValidWord){
                    myAnagramList.add(dictionaryWord.getKey());
                }

            }


        }
        List<String> myReturnAnagram = new ArrayList<>();

        List<String> myAnagramListOrdered = myAnagramList.stream().sorted().collect(Collectors.toList());

        myMagicMethod(myAnagramListOrdered, myReturnAnagram, 0, "", dictionaryCountLetter, myPhrase) ;

        return myReturnAnagram;


    }

    private Map<String, Integer> myPhraseHashMap(char[] myPhraseCharArray) {
        Map<String, Integer> phraseCountMap;
        phraseCountMap = new HashMap<>();

        for (char c : myPhraseCharArray) {
            Integer myInteger = phraseCountMap.get(String.valueOf(c));
            if (myInteger == null) {
                phraseCountMap.put(String.valueOf(c), 1);
            } else {
                phraseCountMap.put(String.valueOf(c), ++myInteger);
            }

        }
        return phraseCountMap;
    }

    private void myMagicMethod(
            List<String> myAnagramListOrdered,
            List<String> myReturnAnagram,
            Integer starterLoop,
            String myReturnPhrase,
            Map<String, Map<String, Integer>> dictionaryCountLetter,
            String myPhrase) {

        for (int i = starterLoop; i < myAnagramListOrdered.size(); i++) {

            String myWordFromAnagram = myAnagramListOrdered.get(i);

            boolean isValidWord = true;

            String myPossibleReturnPhrase = myReturnPhrase;

            Map<String, Integer> stringIntegerMap = dictionaryCountLetter.get(myWordFromAnagram);

            Map<String, Integer> phraseCountMap = myPhraseHashMap(myPhrase.toCharArray());

            for (Map.Entry<String, Integer> entry : stringIntegerMap.entrySet()) {

                String myLetter = entry.getKey();

                Integer myInteger = phraseCountMap.get(myLetter);

                if (entry.getValue() > myInteger) {
                    isValidWord = false;
                }

            }

            if (isValidWord) {

                myPossibleReturnPhrase = myPossibleReturnPhrase + " " + myWordFromAnagram;

                char[] myWordFromAnagramCharArray = myPossibleReturnPhrase.replaceAll(" ","").toCharArray();

                for (char c : myWordFromAnagramCharArray) {

                    Integer myInteger = phraseCountMap.get(String.valueOf(c));
                    if (myInteger != null) {
                        phraseCountMap.put(String.valueOf(c), --myInteger);
                    }
                }
            }

            boolean isValidPhrase = true;

            for (Map.Entry<String, Integer> phraseCountEntry : phraseCountMap.entrySet()) {
                Integer myInteger = phraseCountEntry.getValue();
                if (myInteger > 0) {
                    isValidPhrase = false;
                }
            }

            int lengthOfReturnPhraseWithOutSpaces = myPossibleReturnPhrase.replaceAll(" ", "").length();
            int lengthOfMyPhraseWithOutSpaces = myPhrase.replaceAll(" ", "").length();

            if(lengthOfReturnPhraseWithOutSpaces < lengthOfMyPhraseWithOutSpaces-2) {
                myMagicMethod(myAnagramListOrdered, myReturnAnagram, i + 1, myPossibleReturnPhrase, dictionaryCountLetter, myPhrase);
            }

            if (isValidPhrase && (lengthOfReturnPhraseWithOutSpaces == lengthOfMyPhraseWithOutSpaces)) {
                //TODO: clean empty before get here
                myReturnAnagram.add(myPossibleReturnPhrase.trim());
            }

        }
    }
}
