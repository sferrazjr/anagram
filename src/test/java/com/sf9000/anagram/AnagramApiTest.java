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

    List<String> anagramBestSecret;
    List<String> anagramIT_Crowd;
    List<String> anagramAschheim;

    @PostConstruct
    public void init() {
        anagramBestSecret = new ArrayList<>();
        anagramBestSecret.add("beet crests");
        anagramBestSecret.add("beets crest");
        anagramBestSecret.add("beret sects");
        anagramBestSecret.add("berets sect");
        anagramBestSecret.add("beset crest");
        anagramBestSecret.add("best erects");
        anagramBestSecret.add("best secret");
        anagramBestSecret.add("bests crete");
        anagramBestSecret.add("bests erect");
        anagramBestSecret.add("bet erst sec");
        anagramBestSecret.add("bet rest sec");
        anagramBestSecret.add("bet secrets");
        anagramBestSecret.add("bets erects");
        anagramBestSecret.add("bets secret");
        anagramBestSecret.add("better cess");
        anagramBestSecret.add("betters sec");

        anagramIT_Crowd = new ArrayList<>();
        anagramIT_Crowd.add("cod writ");
        anagramIT_Crowd.add("cord wit");
        anagramIT_Crowd.add("cow dirt");
        anagramIT_Crowd.add("doc writ");
        anagramIT_Crowd.add("tic word");

        anagramAschheim = new ArrayList<>();
        anagramAschheim.add("aches him");
        anagramAschheim.add("ash chime");
        anagramAschheim.add("chase him");
        anagramAschheim.add("chime has");
        anagramAschheim.add("hash mice");
        anagramAschheim.add("hic shame");
        anagramAschheim.add("mice shah");

    }

    @Test
    @DisplayName("Test phrase 'Best Secret' should return HTTP Status 200 and list of 'Best Secret' anagram")
    public void testPhraseBestSecret() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/anagram/solve/Best Secret")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(anagramBestSecret));

    }


    @Test
    @DisplayName("Test phrase 'IT-Crowd' has special and upper case character should return HTTP Status 200 and array with 5 elements")
    public void testPhraseITCrowd() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/anagram/solve/IT-Crowd")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(anagramIT_Crowd));
    }

    @Test
    @DisplayName("Test phrase 'Aschheim' should return HTTP Status 200 and array with 7 elements")
    public void testPhraseAschheim() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/anagram/solve/Aschheim")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(anagramAschheim));

    }

    @Test
    @DisplayName("Test phrase IT is smaller than 3 chars should return HTTP 400")
    public void testPhraseWithOnlyOneWordSmallerThan3Chars() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/anagram/solve/IT")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Word should contain at least 3 characters"));
    }


}
