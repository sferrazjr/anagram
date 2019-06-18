package com.sf9000.anagram.controller;

import com.sf9000.anagram.exception.InvalidWordException;
import com.sf9000.anagram.service.AnagramSolverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    ResponseEntity<?> solve(@PathVariable String word) {
        Calendar calendar = null;
        long startTimeStamp = 0;
        try {

            if (word == null || word.isEmpty() || word.length() < 3) {
                throw new InvalidWordException("Word should contain at least 3 characters");
            }

            calendar = new GregorianCalendar();
            startTimeStamp = calendar.getTimeInMillis();

            List<String> anagramSolved = anagramSolverService.solve(word);

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(anagramSolved);

        } catch (InvalidWordException iwe) {

            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(iwe.getMessage());

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(e.getMessage());

        } finally {
            System.out.println(new GregorianCalendar().getTimeInMillis() - startTimeStamp);
        }

    }

}
