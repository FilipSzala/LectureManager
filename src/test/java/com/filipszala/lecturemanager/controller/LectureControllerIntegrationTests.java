package com.filipszala.lecturemanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filipszala.lecturemanager.controller.dto.lecture.LectureDto;
import com.filipszala.lecturemanager.model.Lecture;
import com.filipszala.lecturemanager.model.Professor;
import com.filipszala.lecturemanager.model.Student;
import com.filipszala.lecturemanager.repository.LectureRepository;
import com.filipszala.lecturemanager.repository.ProfessorRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LectureControllerIntegrationTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    LectureRepository lectureRepository;
    @Autowired
    ProfessorRepository professorRepository;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldGetSingleLecture() throws Exception {
        //given
        Lecture lecture = createListOfLecture().get(0);
        professorRepository.save(lecture.getProfessor());
        lectureRepository.save(lecture);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/lectures/"+lecture.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200))
                .andReturn();

        //then
        LectureDto lectureDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), LectureDto.class);
        assertThat(lectureDto).isNotNull();
        assertThat(lectureDto.getName()).isEqualTo("Filip");
        assertThat(lectureDto.getId()).isEqualTo(1L);
        assertThat(lectureDto.getPlace()).isEqualTo("lecture hall");
        assertThat(lectureDto.getStudentWithoutLectureDtos()).size().isEqualTo(1);
        assertThat(lectureDto.getStudentWithoutLectureDtos().get(0).getSurname()).isEqualTo("Test");
        assertThat(lectureDto.getProfessorWithoutLectureDto()).isNotNull();
        assertThat(lectureDto.getProfessorWithoutLectureDto().getName()).isEqualTo("Test2");

    }
    @Test
    public void shouldGetListOfLecture() throws Exception {
        //given
        List<Lecture> lectures = createListOfLecture();

        professorRepository.save(lectures.get(0).getProfessor());
        professorRepository.save(lectures.get(1).getProfessor());
        lectureRepository.save(lectures.get(0));
        lectureRepository.save(lectures.get(1));

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/lectures"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200))
                .andReturn();

        //then
        LectureDto[] lecturesDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),LectureDto[].class);
        assertThat(lecturesDto).isNotNull();
        assertThat(lecturesDto.length).isEqualTo(2);
        assertThat(lecturesDto[0].getName()).isEqualTo("Filip");

    }

    @Test
    public void shouldPartiallyUpdateLecture() throws Exception {
        Lecture lecture = createListOfLecture().get(0);
        Lecture lecture2 = createListOfLecture().get(1);

        professorRepository.save(lecture.getProfessor());
        professorRepository.save(lecture2.getProfessor());
        lectureRepository.save(lecture);


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .patch("/lectures/{id}",lecture.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lecture2)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();

        LectureDto lectureCheck = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), LectureDto.class);
        assertThat(lectureCheck.getName()).isEqualTo("Filip2");
        assertThat(lectureCheck.getId()).isEqualTo(lecture.getId());

    }

    @Test
    public void shouldUpdateLecture() throws Exception {
        Lecture lecture = createListOfLecture().get(0);
        Lecture lecture2 = createListOfLecture().get(1);

        professorRepository.save(lecture.getProfessor());
        professorRepository.save(lecture2.getProfessor());
        lectureRepository.save(lecture);


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/lectures/{id}",lecture.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lecture2)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();

        LectureDto lectureCheck = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), LectureDto.class);
        assertThat(lectureCheck.getName()).isEqualTo("Filip2");
        assertThat(lectureCheck.getId()).isEqualTo(lecture.getId());
    }
    @Test
    public void shouldCreateLecture() throws Exception {
        Lecture lecture = createListOfLecture().get(0);
        Professor professor = createProfessor();
        professorRepository.save(professor);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/lectures/professors/{id}",professor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lecture)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andReturn();

        LectureDto lectureDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), LectureDto.class);
        assertThat(lectureDto.getName()).isEqualTo(lecture.getName());
        assertThat(lectureDto.getProfessorWithoutLectureDto()).isNotNull();
    }
    @Test
    public void shouldDeleteLecture() throws Exception {
        Lecture lecture = createListOfLecture().get(0);

        professorRepository.save(lecture.getProfessor());
        lectureRepository.save(lecture);


        mockMvc.perform(MockMvcRequestBuilders.delete("/lectures/" + lecture.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    private List<Student> createListOfStudent(){
        Student student = new Student("Test","Test");
        ArrayList<Student> students = new ArrayList<>();
        students.add(student);
        return students;
    }
    private Professor createProfessor(){
        Professor professor = new Professor("Test2","Test2");
        return professor;
    }
    private List <Lecture> createListOfLecture(){
        Lecture lecture = new Lecture();
        lecture.setId(1L);
        lecture.setName("Filip");
        lecture.setPlace("lecture hall");
        lecture.setStudents(createListOfStudent());
        lecture.setProfessor(createProfessor());

        Lecture lecture2 = new Lecture();
        lecture2.setId(2L);
        lecture2.setName("Filip2");
        lecture2.setPlace("lecture hall2");
        lecture2.setStudents(createListOfStudent());
        lecture2.setProfessor(createProfessor());

        List<Lecture> lectures = new ArrayList<>();
        lectures.add(lecture);
        lectures.add(lecture2);
        return lectures;
    }
}


