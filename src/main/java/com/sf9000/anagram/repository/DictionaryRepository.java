package com.sf9000.anagram.repository;


import com.sf9000.anagram.model.BinaryEquivalency;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DictionaryRepository {

    private DictionaryRepository() {
        loadDictionary();
    }

    public static DictionaryRepository getInstance(){
        if(dictionaryRepository==null){
            dictionaryRepository = new DictionaryRepository();
        }

        return dictionaryRepository;
    }

    public Map<String, Integer> getDictionary() {
        return dictionary;
    }

    public Map<String, Map<Character, Integer>> getDictionaryCountLetter() {
        return dictionaryCountLetter;
    }

    private static DictionaryRepository dictionaryRepository;

    private static Map<String, Integer> dictionary = new HashMap<>();

    private static Map<String, Map<Character, Integer>> dictionaryCountLetter = new HashMap<>();

    private void loadDictionary() {

        ClassLoader classLoader = getClass().getClassLoader();
        try (Scanner scanner = new Scanner(new File(classLoader.getResource("anagramDic.txt").getFile()))) {
            while (scanner.hasNext()) {
                String dictionaryWord = scanner.nextLine();
                if (dictionaryWord.length() > 2) {

                    int wordInBinary = 0;

                    dictionaryCountLetter.put(dictionaryWord, new HashMap<>());

                    for (char letter : dictionaryWord.toCharArray()) {
                        Integer binaryEquivalent = BinaryEquivalency.BINARY_EQUIVALENCY.get(letter);

                        if (binaryEquivalent != null) {
                            wordInBinary = wordInBinary | binaryEquivalent;
                            dictionary.put(dictionaryWord, wordInBinary);

                            Map<Character, Integer> letterCountMap = dictionaryCountLetter.get(dictionaryWord);

                            Integer letterCount = letterCountMap.get(letter);
                            if (letterCount == null) {
                                letterCountMap.put(letter, 1);
                            } else {
                                letterCountMap.put(letter, ++letterCount);
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            //FIXME: MISSING ERROR MESSAGE
            e.printStackTrace();
        }

    }


}
