
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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

    @Test
    @Transactional
    public void shouldGetListOfProfessor() throws Exception{
        List<Professor> professors = createListOfProfessor();
        lectureRepository.save(professors.get(0).getLectures().get(0));
        lectureRepository.save(professors.get(0).getLectures().get(1));
        professorRepository.save(professors.get(0));
        professorRepository.save(professors.get(1));


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/professors"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        ProfessorDto [] professorDtos = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),ProfessorDto[].class);
        assertThat(professorDtos).isNotNull();
        assertThat(professorDtos.length).isEqualTo(2);
    }

    @Test
    @Transactional
    public void shouldCreateProfessor() throws Exception{
        Professor professor = createProfessor();
        professor.setLectures(new ArrayList<>());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/professors")
                        .content(objectMapper.writeValueAsString(professor))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        ProfessorDto professorDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),ProfessorDto.class);
        assertThat(professorDto).isNotNull();
        assertThat(professorDto.getId()).isEqualTo(1L);
    }

    @Test
    @Transactional
    public void shouldUpdateProfessor() throws Exception{
        Professor professor = createProfessor();
        professor.setLectures(new ArrayList<>());
        professorRepository.save(professor);

        Professor newProfessor = new Professor();
        newProfessor.setId(1L);
        newProfessor.setName("new name");
        newProfessor.setSurname("new surname");

        professorRepository.save(newProfessor);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/professors/"+professor.getId())
                        .content(objectMapper.writeValueAsString(newProfessor))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ProfessorDto professorDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),ProfessorDto.class);
        assertThat(professorDto).isNotNull();
        assertThat(professorDto.getId()).isEqualTo(1L);
        assertThat(professorDto.getName()).isEqualTo("new name");
    }

    @Test
    @Transactional
    public void shouldPartiallyUpdateProfessor() throws Exception{
        Professor professor = createProfessor();
        professor.setLectures(new ArrayList<>());
        professorRepository.save(professor);

        Professor newProfessor = new Professor();
        newProfessor.setSurname("new surname");

        professorRepository.save(newProfessor);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .patch("/professors/"+professor.getId())
                        .content(objectMapper.writeValueAsString(newProfessor))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ProfessorDto professorDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),ProfessorDto.class);
        assertThat(professorDto).isNotNull();
        assertThat(professorDto.getId()).isEqualTo(1L);
        assertThat(professorDto.getName()).isEqualTo("Test");
        assertThat(professorDto.getSurname()).isEqualTo("new surname");
    }

    @Test
    @Transactional
    public void shouldDeleteProfessor() throws  Exception{
        Professor professor = createProfessor();

        lectureRepository.save(professor.getLectures().get(0));
        lectureRepository.save(professor.getLectures().get(1));
        professorRepository.save(professor);


        mockMvc.perform(MockMvcRequestBuilders.delete("/professors/"+professor.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

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

    private List<Professor> createListOfProfessor(){
        ArrayList<Professor> professors = new ArrayList<>();
        professors.add(createProfessor());
        Professor professor = createProfessor();
        professor.setId(2L);
        professors.add(professor);
        return professors;
    }
}

