package com.sf9000.anagram;

import com.sf9000.anagram.controller.AnagramSolverController;
import com.sf9000.anagram.service.AnagramSolverService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnagramApplicationTests {

	private MockMvc mockMvc;

	@InjectMocks
	private AnagramSolverController anagramSolverController;

	@InjectMocks
	private AnagramSolverService anagramSolverService;

	@Test
	public void contextLoads() {

		MockitoAnnotations.initMocks(this);

		


	}

}
