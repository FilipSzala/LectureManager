package com.filipszala.lecturemanager.repository;

import com.filipszala.lecturemanager.model.Lecture;
import com.filipszala.lecturemanager.model.Professor;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Getter
@Setter
public class LectureRepositoryTests {
    @Autowired
    LectureRepository lectureRepository;
    @Autowired
    ProfessorRepository professorRepository;


    @Test
    public void ShouldReturnLectures(){
        addLecturesToDataBase(5);
        List<Professor> professors = professorRepository.findAll();
        List<Long> ids = professors.stream()
                        .map(professor -> professor.getId())
                                .collect(Collectors.toList());


        List<Lecture> lectures = lectureRepository.findAllByProfessorIdIn(ids);
        assertThat(lectures).isNotNull();
        assertThat(lectures.get(0).getName()).isEqualTo("Test1");
        assertThat(lectures.get(3).getName()).isEqualTo("Test4");
        assertThat(lectures.get(0).getId()).isEqualTo(professors.get(0).getId());
        assertThat(lectures.size()).isEqualTo(5);

    }

    private void addProfessorsToDataBase(int count){
        for (int i = 0; i < count; i++) {
            Professor professor = Professor.builder().id(i+1L).name("Test").surname("Test").lectures(null).build();
            professorRepository.save(professor);
        }
    }
    private void addLecturesToDataBase(int count){
        addProfessorsToDataBase(count);
        for (int i = 0; i < count; i++) {
            Lecture lecture = Lecture.builder().id(i+1L).name("Test"+(i+1)).place("Test"+(i+1)).professor(professorRepository.findById(i+1L).orElseThrow()).build();
            lectureRepository.save(lecture);
        }
    }

}
