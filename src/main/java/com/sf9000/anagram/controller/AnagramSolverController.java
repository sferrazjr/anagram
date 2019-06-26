package com.sf9000.anagram.controller;

import com.sf9000.anagram.exception.InvalidInputException;
import com.sf9000.anagram.service.AnagramSolverService;
import com.sf9000.anagram.util.ContentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@RestController
@RequestMapping(value = "/anagram")
public class AnagramSolverController {

    @Autowired
    AnagramSolverService anagramSolverService;

    @GetMapping("/solve/{word}")
    public ResponseEntity<List<String>> solve(@PathVariable String word) throws InvalidInputException {

        ContentUtil.wordInputValidation(word);

        Calendar calendar = new GregorianCalendar();
        long startTimeStamp = calendar.getTimeInMillis();

        List<String> anagramSolved = anagramSolverService.solve(word);

        double timeElapsed = (new GregorianCalendar().getTimeInMillis() - startTimeStamp) / 1000.0;

        System.out.printf("Anagrams of %s found in %.3f seconds.\n", word, timeElapsed);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(anagramSolved);
    }

}
