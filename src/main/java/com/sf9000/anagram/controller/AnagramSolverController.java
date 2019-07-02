package com.sf9000.anagram.controller;

import com.sf9000.anagram.exception.InvalidInputException;
import com.sf9000.anagram.service.AnagramSolverService;
import com.sf9000.anagram.util.ContentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.GregorianCalendar;
import java.util.List;

import static net.logstash.logback.argument.StructuredArguments.kv;

@RestController
@RequestMapping(value = "/anagram")
public class AnagramSolverController {

    private final static Logger logger = LoggerFactory.getLogger(AnagramSolverController.class);

    final AnagramSolverService anagramSolverService;

    public AnagramSolverController(AnagramSolverService anagramSolverService) {
        this.anagramSolverService = anagramSolverService;
    }

    @GetMapping("/solve/{word}")
    public ResponseEntity<List<String>> solve(@PathVariable String word) throws InvalidInputException {

        ContentUtil.wordInputValidation(word);

        long startTimeStamp = new GregorianCalendar().getTimeInMillis();

        List<String> anagramSolved = anagramSolverService.solve(word);

        long timeElapsed = new GregorianCalendar().getTimeInMillis() - startTimeStamp;

        logger.info("anagrams found {} {} {}", kv("phrase", word), kv("quantity", anagramSolved.size()), kv("timeElapsed", timeElapsed));

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(anagramSolved);
    }

}
