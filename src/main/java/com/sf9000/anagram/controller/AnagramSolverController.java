package com.sf9000.anagram.controller;

import com.sf9000.anagram.service.AnagramSolverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/anagram")
public class AnagramSolverController {

    @Autowired
    AnagramSolverService anagramSolverService;

    @RequestMapping("/solve/{word}")
    @ResponseBody
    List<String> solve(@PathVariable String word){
        try {
            return anagramSolverService.solve(word);
        } catch (Exception e) {
            //TODO return HTTP 400 when word is not valid
            e.printStackTrace();
        }
        return null;
    }

}
