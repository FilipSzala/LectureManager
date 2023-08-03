package com.filipszala.lecturemanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filipszala.lecturemanager.model.Lecture;
import com.filipszala.lecturemanager.model.Professor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class LectureControllerIntegrationTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void displayAllLecture_lecturesExist_returnLectures() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/lectures"))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        Lecture [] lectures = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Lecture[].class);
        Assertions.assertEquals("Math",lectures[0].getName());
    }
}
