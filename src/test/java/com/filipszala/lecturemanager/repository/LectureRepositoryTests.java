package com.filipszala.lecturemanager.repository;

import com.filipszala.lecturemanager.model.Lecture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LectureRepositoryTests {
    @Autowired
    LectureRepository lectureRepository;

    @Test
    public void findLectureByName_NameExist_ReturnLecture(){
        Lecture lecture = Lecture.builder().name("Test").build();
        lectureRepository.save(lecture);
        List<Lecture> lectures = lectureRepository.findLecturesByName("Test");
        Assertions.assertThat(lectures).isNotEmpty();
    }

    @Test
    public void findLectureByName_NameDoesNotExist_ReturnLecture(){
        List<Lecture> lectures = lectureRepository.findLecturesByName(null);
        Assertions.assertThat(lectures).isEmpty();
    }
    @Test
    public void findLectureByName_NameIsNull_ReturnLecture(){
        Lecture lecture = Lecture.builder().name(null).build();
        lectureRepository.save(lecture);
        List<Lecture> lectures = lectureRepository.findLecturesByName(null);
        Assertions.assertThat(lectures).isEmpty();
    }

}
