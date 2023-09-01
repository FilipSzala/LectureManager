package com.filipszala.lecturemanager.controller;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.filipszala.lecturemanager.controller.dto.lecture.LectureDto;
import com.filipszala.lecturemanager.model.Lecture;
import com.filipszala.lecturemanager.model.Professor;
import com.filipszala.lecturemanager.model.Student;
import com.filipszala.lecturemanager.repository.LectureRepository;
import com.filipszala.lecturemanager.repository.ProfessorRepository;
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
import java.util.List;

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
    ProfessorRepository professorRepository;
    @Autowired
    LectureService lectureService;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Transactional
    public void shouldGetSingleLecture() throws Exception {
        //given
        Student student = new Student("Test","Test");
        ArrayList<Student> students = new ArrayList<>();
        students.add(student);

        Professor professor = new Professor("Test2","Test2");

        Lecture newLecture = new Lecture();
        newLecture.setName("Filip");
        newLecture.setPlace("lecture hall");
        newLecture.setStudents(students);
        newLecture.setProfessor(professor);

        lectureRepository.save(newLecture);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/lectures/"+newLecture.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        //then
        LectureDto lecture = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), LectureDto.class);
        assertThat(lecture).isNotNull();
        assertThat(lecture.getName()).isEqualTo("Filip");
        assertThat(lecture.getId()).isEqualTo(1L);
        assertThat(lecture.getPlace()).isEqualTo("lecture hall");
        assertThat(lecture.getStudentWithoutLectureDtos()).size().isEqualTo(1);
        assertThat(lecture.getStudentWithoutLectureDtos().get(0).getSurname()).isEqualTo("Test");
        assertThat(lecture.getProfessorWithoutLectureDto()).isNotNull();
        assertThat(lecture.getProfessorWithoutLectureDto().getName()).isEqualTo("Test2");

    }
    @Test
    @Transactional
    public void shouldGetListOfLecture() throws Exception {
        //given
        Student student = new Student("Test","Test");
        ArrayList<Student> students = new ArrayList<>();
        students.add(student);

        Professor professor = new Professor("Test2","Test2");

        Lecture lecture = new Lecture();
        lecture.setName("Filip");
        lecture.setPlace("lecture hall");
        lecture.setStudents(students);
        lecture.setProfessor(professor);

        Lecture lecture2 = new Lecture();
        lecture2.setName("Filip");
        lecture2.setPlace("lecture hall");
        lecture2.setStudents(students);
        lecture2.setProfessor(professor);

        professorRepository.save(professor);
        lectureRepository.save(lecture);
        lectureRepository.save(lecture2);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/lectures"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        //then
        LectureDto[] lectures = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),LectureDto[].class);
        assertThat(lectures).isNotNull();
        assertThat(lectures.length).isEqualTo(2);
        assertThat(lectures[0].getName()).isEqualTo("Filip");

    }
    @Test
    public void shouldGetSingelLectureById() throws Exception {
        Professor professor = new Professor("Test","Test");


        Lecture lecture = new Lecture();
        lecture.setId(1L);
        lecture.setName("Filip");
        lecture.setPlace("lecture hall");
        lecture.setProfessor(professor);

        professorRepository.save(professor);
        lectureRepository.save(lecture);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/lectures/"+lecture.getId()))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();

        LectureDto lectureDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), LectureDto.class);
        assertThat(lectureDto).isNotNull();
        assertThat(lectureDto).isInstanceOf(LectureDto.class);

    }

    @Test
    public void test() throws Exception {
        Professor professor = new Professor("Test","Test");


        Lecture lecture = new Lecture();
        lecture.setId(1L);
        lecture.setName("Filip");
        lecture.setPlace("lecture hall");
        lecture.setProfessor(professor);

        professorRepository.save(professor);
        lectureRepository.save(lecture);

        lecture.setName("Test");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch("/lectures/"+lecture.getId()))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();

        LectureDto lectureDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), LectureDto.class);
        assertThat(lecture.getName()).isEqualTo("Test");

    }
}


