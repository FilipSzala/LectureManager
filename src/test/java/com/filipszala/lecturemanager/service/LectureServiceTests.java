package com.filipszala.lecturemanager.service;

import com.filipszala.lecturemanager.model.Lecture;
import com.filipszala.lecturemanager.model.Student;
import com.filipszala.lecturemanager.repository.LectureRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LectureServiceTests {
    @Mock
    private LectureRepository lectureRepository;
    @Mock
    private LectureService lectureServiceMock;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private StudentService studentService;
    @InjectMocks
    private LectureService lectureService;

    @Test
    void saveLecture_correctLecture_returnLecture(){
        Lecture lecture = Lecture.builder()
                .name("Test")
                .place("Test")
                .build();
        when(lectureRepository.save(Mockito.any(Lecture.class))).thenReturn(lecture);
        Lecture savedLecture = lectureRepository.save(lecture);
        Assertions.assertThat(savedLecture).isNotNull();
    }
    @Test
    void saveLecture_nameIsNull_returnException(){
        Lecture lecture = Lecture.builder()
                .name(null)
                .place("Test")
                .build();
        assertThatThrownBy(() -> lectureService.save(lecture))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    void saveLecture_lectureIsNull_returnException(){
        Lecture lecture = null;
        assertThatThrownBy(() -> lectureService.save(lecture))
                .isInstanceOf(NullPointerException.class);
    }

    @Test void updateLecture_correctLectureAndId_returnLecture(){
        Long id = 1L;
        List<Student> students = new ArrayList<>();
        students.add(new Student());
        Lecture oldLecture = Lecture.builder().build();
        Lecture lecture = Lecture.builder()
                .name("test")
                .place("test")
                .professorId(1L)
                .students(students)
                .build();
        when(lectureRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(oldLecture));
        when(lectureRepository.save(Mockito.any(Lecture.class))).thenReturn(lecture);
        Lecture updatedLecture = lectureService.updateLecture(id,lecture);
        Assertions.assertThat(updatedLecture).isNotNull();
    }
    @Test void updateLecture_incorrectLecture_returnException(){
        Long id = 1L;
        List<Student> students = new ArrayList<>();
        students.add(new Student());
        Lecture lecture = Lecture.builder()
                .name(null)
                .place("test")
                .professorId(1L)
                .students(students)
                .build();
       assertThatThrownBy(()->lectureService.updateLecture(id,lecture)).isInstanceOf(IllegalArgumentException.class);
    }
    @Test void updateLecture_incorrectId_returnException(){
        Long id = null;
        List<Student> students = new ArrayList<>();
        students.add(new Student());
        Lecture lecture = Lecture.builder()
                .name("test")
                .place("test")
                .professorId(1L)
                .students(students)
                .build();
        assertThatThrownBy(()->lectureService.updateLecture(id,lecture)).isInstanceOf(NullPointerException.class);
    }
    @Test void partiallyUpdateLecture_correctLectureAndId_returnLecture(){
        Long id = 1L;
        List<Student> students = new ArrayList<>();
        students.add(new Student());
        Lecture oldLecture = Lecture.builder().build();
        Lecture lecture = Lecture.builder()
                .name("test")
                .place("test")
                .professorId(1L)
                .students(students)
                .build();
        when(lectureRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(oldLecture));
        when(lectureRepository.save(Mockito.any(Lecture.class))).thenReturn(lecture);
        Lecture updatedLecture = lectureService.partiallyUpdateLecture(id,lecture);
        Assertions.assertThat(updatedLecture).isNotNull();
    }
    @Test void partiallyUpdateLecture_incorrecLecture_returnException(){
        Lecture lecture=null;
        assertThatThrownBy(() -> lectureService.partiallyUpdateLecture(1L,lecture))
                .isInstanceOf(NullPointerException.class);
    }
    @Test void partiallyUpdateLecture_incorrecId_returnException(){
        List<Student> students = new ArrayList<>();
        students.add(new Student());
        Lecture lecture = Lecture.builder()
                .name("test")
                .place("test")
                .professorId(1L)
                .students(students)
                .build();
        assertThatThrownBy(() -> lectureService.partiallyUpdateLecture(0L,lecture))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    void findLectureById_correctId_returnLecture(){
        Long id = 1L;
        Lecture lecture = Lecture.builder().build();
        when(lectureRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(lecture));
        Optional<Lecture> foundLecture = lectureService.findLectureById(id);
        Assertions.assertThat(foundLecture).isNotNull();
    }

    @Test
    void findLectureById_idLessThanExpected_returnException(){
        Long id = 0L;
        assertThatThrownBy(() -> lectureService.findLectureById(id))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    void findLectureById_idIsNull_returnException(){
        Long id = null;
        assertThatThrownBy(() -> lectureService.findLectureById(id))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void deleteLectureById_correctId_deleteLecture(){
        Lecture lecture = Lecture.builder().id(1L).build();
        when (lectureRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.ofNullable(lecture));
        assertAll(() -> lectureService.deleteLecture(lecture.getId()));
    }
    @Test
    public void deleteLectureById_idIsNull_returnException(){
        Lecture lecture = Lecture.builder().id(null).build();
        assertThatThrownBy(() ->  lectureService.deleteLecture(lecture.getId()))
                .isInstanceOf(NullPointerException.class);
    }
    @Test
    public void deleteLectureById_idIsLessThanExpected_returnException(){
        Lecture lecture = Lecture.builder().id(0L).build();
        assertThatThrownBy(() ->  lectureService.deleteLecture(lecture.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findLectureByName_nameIsCorrect_returnLecutre(){
        Lecture lecture = Lecture.builder()
                .name("Filip")
                .build();
        List <Lecture> lectures = new ArrayList<>();
        lectures.add(lecture);
        when(lectureRepository.findLecturesByName(Mockito.any(String.class))).thenReturn(lectures);
        Lecture foundLecture = lectureService.findLectureByName(lecture.getName());
        Assertions.assertThat(foundLecture).isNotNull();
    }
    @Test
    public void findLectureByName_nameIsNull_returnException(){
        assertThatThrownBy(() ->  lectureService.findLectureByName(null))
                .isInstanceOf(NullPointerException.class);
    }
    @Test
    public void findLectureByName_nameIsEmpty_returnException(){
        assertThatThrownBy(() ->  lectureService.findLectureByName(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void selectLecture_lectureNameisNull_selectLecture(){
        assertThatThrownBy(() ->  lectureService.selectLecture(null,1L))
                .isInstanceOf(NullPointerException.class);
}
    @Test
    public void selectLecture_studentIdIsNull_selectLecture(){
        assertThatThrownBy(() ->  lectureService.selectLecture("test",null))
                .isInstanceOf(NullPointerException.class);
    }
}
