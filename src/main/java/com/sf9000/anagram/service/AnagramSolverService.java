package com.sf9000.anagram.service;

import javafx.beans.property.ReadOnlyLongWrapper;
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

    //todo
    /*
    1. order each word from dictionary alphabetically
    2. order anagram word alphabetically
    3. compare values

    note: pay attention to exceptions

     */

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
        for (Map.Entry<String, Integer> dictionaryWord : dictionary.entrySet()) {

            int myAnd = mySumPhrase | dictionaryWord.getValue();
            if ((myAnd ^ mySumPhrase) == 0) {

                Map<String, Integer> stringIntegerMap = dictionaryCountLetter.get(dictionaryWord.getKey());

                boolean isValidWord = true;

                Map<String, Integer> phraseCountMap = new HashMap<>();

                for (Map.Entry<String, Integer> entry : stringIntegerMap.entrySet()) {

                    String myLetter = entry.getKey();

                    Integer myInteger = phraseCountMap.get(myLetter);

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

        myMagicMethod(myAnagramListOrdered, myReturnAnagram) ;

        Map<String, Integer> phraseCountMap = null;

        for(int i = 0; i<myAnagramListOrdered.size();i++){

            String myReturnPhrase = "";

            boolean isValidPhrase = true;

            phraseCountMap = new HashMap<>();

            for (char c : myPhraseCharArray) {
                Integer myInteger = phraseCountMap.get(String.valueOf(c));
                if (myInteger == null) {
                    phraseCountMap.put(String.valueOf(c), 1);
                } else {
                    phraseCountMap.put(String.valueOf(c), ++myInteger);
                }

            }

            for(int j = i; j<myAnagramListOrdered.size();j++){

                String myWordFromAnagram = myAnagramListOrdered.get(j);

                //is valid word?
                Map<String, Integer> stringIntegerMap = dictionaryCountLetter.get(myWordFromAnagram);

                boolean isValidWord = true;

                for (Map.Entry<String, Integer> entry : stringIntegerMap.entrySet()) {

                    String myLetter = entry.getKey();

                    Integer myInteger = phraseCountMap.get(myLetter);

                    if (entry.getValue() > myInteger) {
                        isValidWord = false;
                    }

                }

                if (isValidWord) {
                    myReturnPhrase = myReturnPhrase + " " + myWordFromAnagram;

                    char[] myWordFromAnagramCharArray = myWordFromAnagram.toCharArray();

                    for (char c : myWordFromAnagramCharArray) {

                        Integer myInteger = phraseCountMap.get(String.valueOf(c));
                        if (myInteger != null) {
                            phraseCountMap.put(String.valueOf(c), --myInteger);
                        }

                    }
                }

            }

            for (Map.Entry<String, Integer> dictionaryWord : phraseCountMap.entrySet()) {

                System.out.println(dictionaryWord.getKey() + ":" + dictionaryWord.getValue());

                Integer myInteger = dictionaryWord.getValue();
                if (myInteger != 0) {
                    isValidPhrase = false;
                }

            }

            if (isValidPhrase) {
                myReturnAnagram.add(myReturnPhrase);
            }

        }

        return myReturnAnagram;


    }

    private void myMagicMethod(List<String> myAnagramListOrdered, List<String> myReturnAnagram) {




    }

}
