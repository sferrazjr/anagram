package com.sf9000.anagram.service;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class AnagramSolverService {

    public List<String> solve(final String myPhrase) throws FileNotFoundException {

        Map<String, Integer> equivalentSet = new HashMap<>();
        equivalentSet.put("a", 1);
        equivalentSet.put("b", 2);
        equivalentSet.put("c", 4);
        equivalentSet.put("d", 8);
        equivalentSet.put("e", 16);
        equivalentSet.put("f", 32);
        equivalentSet.put("g", 64);
        equivalentSet.put("h", 128);
        equivalentSet.put("i", 256);
        equivalentSet.put("j", 512);
        equivalentSet.put("k", 1024);
        equivalentSet.put("l", 2048);
        equivalentSet.put("m", 4096);
        equivalentSet.put("n", 8192);
        equivalentSet.put("o", 16384);
        equivalentSet.put("p", 32768);
        equivalentSet.put("q", 65536);
        equivalentSet.put("r", 131072);
        equivalentSet.put("s", 262144);
        equivalentSet.put("t", 524288);
        equivalentSet.put("u", 1048576);
        equivalentSet.put("v", 2097152);
        equivalentSet.put("w", 4194304);
        equivalentSet.put("x", 8388608);
        equivalentSet.put("y", 16777216);
        equivalentSet.put("z", 33554432);

        //declare dictionary
        Map<String, Integer> dictionary = new HashMap<>();

        Map<String, Map<String, Integer>> dictionaryCountLetter = new HashMap<>();

        ClassLoader classLoader = getClass().getClassLoader();
        try (Scanner scanner = new Scanner(new File(classLoader.getResource("anagramDic.txt").getFile()))) {
            while (scanner.hasNext()) {
                String dictionaryWord = scanner.nextLine();
                if (dictionaryWord.length() > 2) {

                    char[] myWordCharArray = dictionaryWord.toCharArray();

                    int mySum = 0;

                    dictionaryCountLetter.put(dictionaryWord, new HashMap<>());

                    for (char c : myWordCharArray) {
                        Integer binaryEquivalent = equivalentSet.get(String.valueOf(c));

                        if (binaryEquivalent != null) {
                            mySum = mySum | binaryEquivalent;
                            dictionary.put(dictionaryWord, mySum);

                            Map<String, Integer> stringIntegerMap = dictionaryCountLetter.get(dictionaryWord);
                            Integer myInteger = stringIntegerMap.get(String.valueOf(c));
                            if (myInteger == null) {
                                stringIntegerMap.put(String.valueOf(c), 1);
                            } else {
                                stringIntegerMap.put(String.valueOf(c), ++myInteger);
                            }
                        }
                    }
                }
            }
        }

        //my phrase representation
        char[] myPhraseCharArray = myPhrase.toCharArray();

        int mySumPhrase = 0;

        for (char c : myPhraseCharArray) {
            Integer binaryEquivalent = equivalentSet.get(String.valueOf(c));
            mySumPhrase = mySumPhrase | binaryEquivalent;
        }

        List<String> myAnagramList = new ArrayList<>();

        Map<String, Integer> myPhraseCountHashMap = myPhraseHashMap(myPhraseCharArray);

        for (Map.Entry<String, Integer> dictionaryWord : dictionary.entrySet()) {

            int myAnd = mySumPhrase | dictionaryWord.getValue();
            if ((myAnd ^ mySumPhrase) == 0) {

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
                myReturnAnagram.add(myPossibleReturnPhrase);
            }

        }
    }
}
