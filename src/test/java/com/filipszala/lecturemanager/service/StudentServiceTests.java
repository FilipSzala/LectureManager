package com.filipszala.lecturemanager.service;

import com.filipszala.lecturemanager.model.Lecture;
import com.filipszala.lecturemanager.model.Student;
import com.filipszala.lecturemanager.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTests {
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentService studentService;

    @Test void saveStudent_correctStudent_returnStudent(){
        Student student = Student.builder()
                .name("Test")
                .surname("Test")
                .build();
        when(studentRepository.save(Mockito.any(Student.class))).thenReturn(student);
        Student savedStudent = studentService.save(student);
        Assertions.assertThat(savedStudent).isNotNull();
    }
    @Test void saveStudent_studentNameIsNull_returnException(){
        Student student = Student.builder()
                .name(null)
                .build();
        assertThatThrownBy(() -> studentService.save(student))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test void saveStudent_studentIsNull_returnException(){
        Student student= Student.builder().build();
        assertThatThrownBy(() -> studentService.save(student))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test void updateStudent_correctStudentAndId_returnStudent(){
        Long id = 1L;
        List<Lecture> lectures = new ArrayList<>();
        lectures.add(new Lecture());
        Student oldStudent = Student.builder().build();
        Student student = Student.builder()
                .name("test")
                .surname("test")
                .id(id)
                .lectures(lectures)
                .build();
        when(studentRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(oldStudent));
        when(studentRepository.save(Mockito.any(Student.class))).thenReturn(student);


        Student updateStudent = studentService.updateStudent(id,student);
        Assertions.assertThat(updateStudent).isNotNull();
        Assertions.assertThat(updateStudent.getName()).isEqualTo("test");
    }
    @Test void updateStudent_incorrectStudent_returnException(){
        Long id = 1L;
        List<Lecture> lectures = new ArrayList<>();
        lectures.add(new Lecture());
        Student student = Student.builder()
                .name(null)
                .surname("test")
                .id(id)
                .lectures(lectures)
                .build();
        assertThatThrownBy(() -> studentService.updateStudent(id,student))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test void updateStudent_incorrectId_returnException(){
        Long id = null;
        List<Lecture> lectures = new ArrayList<>();
        lectures.add(new Lecture());
        Student student = Student.builder()
                .name("test")
                .surname("test")
                .id(1L)
                .lectures(lectures)
                .build();
        assertThatThrownBy(() -> studentService.updateStudent(id,student))
                .isInstanceOf(NullPointerException.class);
    }

    @Test void partiallyUpdateStudent_correctStudentAndId_returnStudent(){
        Long id = 1L;
        List<Lecture> lectures = new ArrayList<>();
        lectures.add(new Lecture());
        Student oldStudent = Student.builder().build();
        Student student = Student.builder()
                .name("test")
                .surname("test")
                .id(id)
                .lectures(lectures)
                .build();
        when(studentRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(oldStudent));
        when(studentRepository.save(Mockito.any(Student.class))).thenReturn(student);


        Student updateStudent = studentService.partiallyUpdateStudent(id,student);
        Assertions.assertThat(updateStudent).isNotNull();
        Assertions.assertThat(updateStudent.getName()).isEqualTo("test");
    }

    @Test void partiallyUpdateStudent_incorrectStudent_returnException(){
        Student student=null;
        assertThatThrownBy(() -> studentService.partiallyUpdateStudent(1L,student))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test void partiallyUpdateStudent_incorrectId_returnException(){
        Long id = null;
        List<Lecture> lectures = new ArrayList<>();
        lectures.add(new Lecture());
        Student student = Student.builder()
                .name("test")
                .surname("test")
                .id(1L)
                .lectures(lectures)
                .build();
        assertThatThrownBy(() -> studentService.partiallyUpdateStudent(id,student))
                .isInstanceOf(NullPointerException.class);
    }
    @Test
    void findStudentById_correctId_returnStudent(){
        Long id = 1L;
        Student student = Student.builder().build();
        when(studentRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(student));
        Optional<Student> foundStudent = studentService.findStudentById(id);
        Assertions.assertThat(foundStudent).isNotNull();
    }
    @Test void findStudentById_idLessThanExpected_ReturnException(){
        Long id = 0L;
        assertThatThrownBy(() -> studentService.findStudentById(id))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test void findStudentById_idIsNull_ReturnException(){
        Long id = null;
        assertThatThrownBy(() -> studentService.findStudentById(id))
                .isInstanceOf(NullPointerException.class);
    }
    @Test
    public void deleteStudentById_correctId_deleteStudent(){
        Student student = Student.builder().id(1L).build();
        when (studentRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.ofNullable(student));
        assertAll(() -> studentService.deleteStudent(student.getId()));
    }
    @Test
    public void deleteStudentById_idIsNull_returnException(){
        Student student = Student.builder().id(null).build();
        assertThatThrownBy(() ->  studentService.deleteStudent(student.getId()))
                .isInstanceOf(NullPointerException.class);
    }
    @Test
    public void deleteStudentById_idLessThanExpected_returnException(){
        Student student = Student.builder().id(0L).build();
        assertThatThrownBy(() ->  studentService.deleteStudent(student.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void selectLecture_lectureIdIsNull_selectLecture(){
        assertThatThrownBy(() ->  studentService.selectLecture(null,1L))
                .isInstanceOf(NullPointerException.class);
    }
    @Test
    public void selectLecture_LectureIdIsLessThanExpected_selectLecture(){
        assertThatThrownBy(() ->  studentService.selectLecture(0L,null))
                .isInstanceOf(NullPointerException.class);
    }
}
