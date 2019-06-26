package com.sf9000.anagram.controller;

import com.sf9000.anagram.exception.InvalidInputException;
import com.sf9000.anagram.service.CounterLettersService;
import com.sf9000.anagram.util.ContentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/counter")
public class CounterLettersController {

    @Autowired
    CounterLettersService counterLettersService;

    @GetMapping("/letters/{word}")
    public ResponseEntity<Integer> countLetters(@PathVariable String word) throws InvalidInputException {

        ContentUtil.wordInputValidation(word);

        int counterLetters = counterLettersService.counterLetters(word);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(counterLetters);

    }

}
