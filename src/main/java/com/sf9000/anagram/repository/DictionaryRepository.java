package com.sf9000.anagram.repository;


import com.sf9000.anagram.model.BinaryEquivalency;
import com.sf9000.anagram.model.DictionaryWord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Repository
public class DictionaryRepository {

    @Value("${anagram.dictionary.file}")
    private String fileName;

    private Map<String, DictionaryWord> dictionaryWordMap = new HashMap<>();

    public Map<String, DictionaryWord> getDictionaryWordMap() {
        return dictionaryWordMap;
    }

    @PostConstruct
    private void init() throws Exception {

        ClassLoader classLoader = getClass().getClassLoader();
        try (Scanner scanner = new Scanner(new File(classLoader.getResource(fileName).getFile()))) {
            while (scanner.hasNext()) {
                String word = scanner.nextLine();
                if (word.length() > 2) {

                    int wordInBinary = 0;

                    DictionaryWord dictionaryWord = new DictionaryWord().setWord(word);

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
                    dictionaryWordMap.put(word, dictionaryWord);
                }
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            throw fileNotFoundException;
        }

    }


}
