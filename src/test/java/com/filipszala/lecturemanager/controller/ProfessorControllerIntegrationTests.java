
package com.filipszala.lecturemanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filipszala.lecturemanager.controller.dto.professor.ProfessorDto;
import com.filipszala.lecturemanager.model.Lecture;
import com.filipszala.lecturemanager.model.Professor;
import com.filipszala.lecturemanager.repository.LectureRepository;
import com.filipszala.lecturemanager.repository.ProfessorRepository;
import jakarta.transaction.Transactional;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfessorControllerIntegrationTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Test
    @Transactional
    public void shouldGetSingleProfessor() throws Exception{
        Professor professor = createProfessor();
        lectureRepository.save(professor.getLectures().get(0));
        lectureRepository.save(professor.getLectures().get(1));
        professorRepository.save(professor);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/professors/"+professor.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        ProfessorDto professorDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),ProfessorDto.class);
                assertThat(professorDto).isNotNull();
                assertThat(professorDto.getName()).isEqualTo("Test");
                assertThat(professorDto.getLectures().get(0).getName()).isEqualTo("Math");
    }

    private Professor createProfessor(){
        Professor professor = new Professor();
        professor.setId(1L);
        professor.setName("Test");
        professor.setSurname("Test");
        professor.setLectures(createLectures());
        return professor;
    }
    private List<Lecture> createLectures(){
        Lecture lecture = new Lecture();
        lecture.setId(1L);
        lecture.setName("Math");
        lecture.setPlace("lecture hall");
        lecture.setStudents(null);
        lecture.setProfessor(null);

        Lecture lecture2 = new Lecture();
        lecture2.setId(2L);
        lecture2.setName("Math2");
        lecture2.setPlace("lecture hall2");
        lecture2.setStudents(null);
        lecture2.setProfessor(null);

        List<Lecture> lectures = new ArrayList<>();
        lectures.add(lecture);
        lectures.add(lecture2);
        return lectures;
    }
}

