package com.filipszala.lecturemanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filipszala.lecturemanager.model.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerIntegrationTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void displayAllStudent_studentsExist_returnStudents() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/students"))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        Student[] students =objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Student[].class);
        Assertions.assertEquals("Filip",students[0].getName());
    }
}
