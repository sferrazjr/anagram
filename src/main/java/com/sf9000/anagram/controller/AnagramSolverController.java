package com.sf9000.anagram.controller;

import com.sf9000.anagram.service.AnagramSolverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping(value = "/anagram")
public class AnagramSolverController {

    @Autowired
    AnagramSolverService anagramSolverService;

    @RequestMapping("/solve/{word}")
    @ResponseBody
    List<String> solve(@PathVariable String word) {
        Calendar calendar = null;
        long startTimeStamp = 0;
        try {
            calendar = new GregorianCalendar();
            startTimeStamp = calendar.getTimeInMillis();

            return anagramSolverService.solve(word);


        } catch (Exception e) {
            //TODO return HTTP 400 when word is not valid
            e.printStackTrace();
        } finally {
            System.out.println(new GregorianCalendar().getTimeInMillis() - startTimeStamp);
        }
        return null;
    }

}
