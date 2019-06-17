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

    public Map<String, Map<String, Integer>> getDictionaryCountLetter() {
        return dictionaryCountLetter;
    }

    private static DictionaryRepository dictionaryRepository;

    private static Map<String, Integer> dictionary = new HashMap<>();

    private static Map<String, Map<String, Integer>> dictionaryCountLetter = new HashMap<>();

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

                            Map<String, Integer> stringIntegerMap = dictionaryCountLetter.get(dictionaryWord);

                            Integer letterCount = stringIntegerMap.get(String.valueOf(letter));
                            if (letterCount == null) {
                                stringIntegerMap.put(String.valueOf(letter), 1);
                            } else {
                                stringIntegerMap.put(String.valueOf(letter), ++letterCount);
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
