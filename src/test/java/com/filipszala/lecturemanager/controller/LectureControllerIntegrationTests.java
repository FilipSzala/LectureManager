/*
package com.filipszala.lecturemanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filipszala.lecturemanager.model.Lecture;
import com.filipszala.lecturemanager.model.Professor;
import com.filipszala.lecturemanager.model.Student;
import com.filipszala.lecturemanager.repository.LectureRepository;
import com.filipszala.lecturemanager.repository.StudentRepository;
import com.filipszala.lecturemanager.service.LectureService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class LectureControllerIntegrationTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    LectureRepository lectureRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    LectureService lectureService;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void displayAllLecture_lecturesExist_return200() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/lectures"))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        Lecture [] lectures = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Lecture[].class);
        Assertions.assertEquals("Math",lectures[0].getName());
    }
    @Test
    public void displayLectureById_idExists_return200() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/lectures/1"))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
    }

    @Test
    @Transactional
    public void shouldGetSingleStudent() throws Exception {
        //given
        Student student = new Student("Test","Test");
        studentRepository.save(student);

        Lecture newLecture = new Lecture();
        newLecture.setName("Filip");
        newLecture.setPlace("lecture hall");
        lectureRepository.save(newLecture);

        lectureService.selectLecture(newLecture.getName(),student.getStudentId());

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/lectures/"+newLecture.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();


        //then
        Lecture lecture = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Lecture.class);
        assertThat(lecture).isNotNull();
        assertThat(lecture.getName()).isEqualTo("Filip");
        assertThat(lecture.getId()).isEqualTo(2L);
        assertThat(lecture.getPlace()).isEqualTo("lecture hall");
        assertThat(lecture.getStudentsList()).size().isEqualTo(1);
        assertThat(lecture.getStudentsList().get(0).getSurname()).isEqualTo("Test");

    }

}
*/
