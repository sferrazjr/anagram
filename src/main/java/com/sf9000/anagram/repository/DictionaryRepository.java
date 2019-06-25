package com.sf9000.anagram.repository;


import com.sf9000.anagram.model.WordEquivalency;
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

    private Map<String, WordEquivalency> dictionaryWordMap = new HashMap<>();

    public Map<String, WordEquivalency> getDictionaryWordMap() {
        return dictionaryWordMap;
    }

    @PostConstruct
    private void init() throws Exception {

        ClassLoader classLoader = getClass().getClassLoader();
        try (Scanner scanner = new Scanner(new File(classLoader.getResource(fileName).getFile()))) {
            while (scanner.hasNext()) {
                String word = scanner.nextLine();
                if (word.length() > 2) {

                    dictionaryWordMap.put(word, new WordEquivalency().create(word));

                }
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            throw fileNotFoundException;
        }

    }


}
