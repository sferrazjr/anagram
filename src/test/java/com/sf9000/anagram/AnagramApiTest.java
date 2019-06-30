package com.sf9000.anagram;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AnagramApiTest {

    @Autowired
    private MockMvc mockMvc;

    List<String> anagramIT_Crowd;

    @PostConstruct
    public void init() {

        anagramIT_Crowd = new ArrayList<>();
        anagramIT_Crowd.add("cod writ");
        anagramIT_Crowd.add("cord wit");
        anagramIT_Crowd.add("cow dirt");
        anagramIT_Crowd.add("doc writ");
        anagramIT_Crowd.add("tic word");

    }

    @Test
    @DisplayName("Solve anagram of 'IT-Crowd' has special and upper case character should return HTTP Status 200 and array with 5 elements")
    public void solveAnagramITCrowd() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/anagram/solve/IT-Crowd")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(anagramIT_Crowd));
    }

    @Test
    @DisplayName("Solve anagram of IT is smaller than 3 chars should return HTTP 400")
    public void solveAnagramWithOnlyOneWordSmallerThan3Chars() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/anagram/solve/IT")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Word should contain at least 3 characters"));
    }

    @Test
    @DisplayName("Count letters of a word Maria should return HTTP Status 200 and integer 4")
    public void countLetterOfMaria() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/counter/letters/Maria")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(4));

    }

    @Test
    @DisplayName("Count letters of word IT is smaller than 3 chars should return HTTP 400")
    public void countLettersOfWordSmallerThan3Chars() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/counter/letters/IT")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Word should contain at least 3 characters"));
    }

}
