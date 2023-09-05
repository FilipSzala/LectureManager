package com.filipszala.lecturemanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filipszala.lecturemanager.controller.dto.student.StudentDto;
import com.filipszala.lecturemanager.model.Lecture;
import com.filipszala.lecturemanager.model.Student;
import com.filipszala.lecturemanager.repository.LectureRepository;
import com.filipszala.lecturemanager.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StudentControllerIntegrationTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    LectureRepository lectureRepository;

    private Student student;

    private Lecture lecture;
    private List<Lecture> lectures;

    @BeforeEach
    public void init() {
        lecture = Lecture.builder().id(1L).name("Math").place("Hall").build();
        lectures = createLectures();
        student = Student.builder().id(1L).name("Test").surname("Test").lectures(lectures).build();
    }

    @Test
    public void shouldCreateStudent () throws  Exception{
        lectureRepository.save(student.getLectures().get(0));


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        StudentDto studentDto= objectMapper.readValue(mvcResult.getResponse().getContentAsString(),StudentDto.class);
        assertThat(studentDto).isNotNull();
        assertThat(studentDto.getName()).isEqualTo("Test");
    }
    @Test
    public void shouldGetAllStudents () throws Exception {
        lectureRepository.save(student.getLectures().get(0));
        studentRepository.save(student);

                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/students"))
                .andExpect(MockMvcResultMatchers.status()
                .is(200))
                .andReturn();


        StudentDto[] studentDtos =objectMapper.readValue(mvcResult.getResponse().getContentAsString(), StudentDto[].class);
        Assertions.assertEquals("Test",studentDtos[0].getName());
    }

    @Test
    public void shouldGetStudentById () throws Exception {
        lectureRepository.save(student.getLectures().get(0));
        studentRepository.save(student);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/"+student.getId()))
                .andExpect(MockMvcResultMatchers.status()
                        .is(200))
                .andReturn();


        StudentDto studentDto =objectMapper.readValue(mvcResult.getResponse().getContentAsString(), StudentDto.class);
        Assertions.assertEquals("Test",studentDto.getName());
    }

    @Test
    public void shouldUpdateStudent () throws Exception{
        lectureRepository.save(student.getLectures().get(0));
        studentRepository.save(student);
        Student newStudent = Student.builder().id(2L).name("new name").surname("new surname").lectures(lectures).build();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .put("/students/"+student.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newStudent)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        StudentDto studentDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),StudentDto.class);

        assertThat(studentDto).isNotNull();
        assertThat(studentDto.getName()).isEqualTo("new name");
    }

    @Test
    public void shouldPartiallyUpdateStudent () throws Exception{
        lectureRepository.save(student.getLectures().get(0));
        studentRepository.save(student);
        Student newStudent = Student.builder().id(2L).name("new name").surname(null).lectures(lectures).build();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .patch("/students/"+student.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStudent)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        StudentDto studentDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),StudentDto.class);

        assertThat(studentDto).isNotNull();
        assertThat(studentDto.getName()).isEqualTo("new name");
        assertThat(studentDto.getSurname()).isEqualTo(student.getSurname());
    }

    @Test
    public void  shouldDeleteStudent() throws Exception {
        lectureRepository.save(student.getLectures().get(0));
        studentRepository.save(student);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .delete("/students/"+student.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();
    }

    @Test
    public void shouldSelectLecture() throws Exception{

        student.setLectures(null);
        lecture.setStudents(null);
        lectureRepository.save(lecture);
        studentRepository.save(student);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .patch("/students/lectures/"+lecture.getId()+"/"+student.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MvcResult mvcResultForStudent = mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/"+student.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();


        StudentDto studentDto = objectMapper.readValue(mvcResultForStudent.getResponse().getContentAsString(),StudentDto.class);
        assertThat(studentDto.getLectures()).isNotNull();
        assertThat(studentDto.getLectures().get(0).getName()).isEqualTo("Math");
    }

    private List <Lecture> createLectures() {
        ArrayList<Lecture> lectures = new ArrayList<>();
        lectures.add(lecture);
        return lectures;
    }
}

