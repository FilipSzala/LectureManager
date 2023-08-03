package com.filipszala.lecturemanager.service;

import com.filipszala.lecturemanager.model.Lecture;
import com.filipszala.lecturemanager.model.Professor;
import com.filipszala.lecturemanager.model.Student;
import com.filipszala.lecturemanager.repository.ProfessorRepository;
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
public class ProfessorServiceTests {
    @Mock
    private ProfessorRepository professorRepository;
    @InjectMocks
    private ProfessorService professorService;

    @Test
    void saveProfessor_correctProfessor_returnProfessor(){
        Professor professor = Professor.builder()
                .name("Test")
                .surname("Test")
                .build();
        when(professorRepository.save(Mockito.any(Professor.class))).thenReturn(professor);
        Professor savedProfessor = professorRepository.save(professor);
        Assertions.assertThat(savedProfessor).isNotNull();
    }
    @Test
    void saveProfessor_nameIsNull_returnException(){
        Professor professor = Professor.builder()
                .name(null)
                .surname("Test")
                .build();
        assertThatThrownBy(() -> professorService.save(professor))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    void saveProfessor_professorIsNull_returnException(){
        Professor professor = null;
        assertThatThrownBy(() -> professorService.save(professor))
                .isInstanceOf(NullPointerException.class);
    }

    @Test void updateProfessor_correctProfessorAndId_returnProfessor(){
        Long id = 1L;
        List<Lecture> lectures = new ArrayList<>();
        lectures.add(new Lecture());
        Professor oldProfessor = Professor.builder().build();
        Professor professor = Professor.builder()
                .name("test")
                .surname("test")
                .professorId(1L)
                .lectures(lectures)
                .build();
        when(professorRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(oldProfessor));
        when(professorRepository.save(Mockito.any(Professor.class))).thenReturn(professor);


        Professor updateProfessor = professorService.updateProffesor(id,professor);
        Assertions.assertThat(updateProfessor).isNotNull();
        Assertions.assertThat(updateProfessor.getName()).isEqualTo("test");
    }
    @Test void updateProfessor_incorrectProfessor_returnException(){
        Long id = 1L;
        List<Lecture> lectures = new ArrayList<>();
        lectures.add(new Lecture());
        Professor professor = Professor.builder()
                .name(null)
                .surname("test")
                .professorId(1L)
                .lectures(lectures)
                .build();
        assertThatThrownBy(() -> professorService.updateProffesor(id,professor))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test void updateProfessor_incorrectId_returnException(){
        Long id = null;
        List<Lecture> lectures = new ArrayList<>();
        lectures.add(new Lecture());
        Professor professor = Professor.builder()
                .name("test")
                .surname("test")
                .professorId(1L)
                .lectures(lectures)
                .build();
        assertThatThrownBy(() -> professorService.updateProffesor(id,professor))
                .isInstanceOf(NullPointerException.class);
    }

    @Test void partiallyUpdateProfessor_correctProfessorAndId_returnProfessor(){
        Long id = 1L;
        List<Lecture> lectures = new ArrayList<>();
        lectures.add(new Lecture());
        Professor oldProfessor = Professor.builder().build();
        Professor professor = Professor.builder()
                .name("test")
                .surname("test")
                .professorId(1L)
                .lectures(lectures)
                .build();
        when(professorRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(oldProfessor));
        when(professorRepository.save(Mockito.any(Professor.class))).thenReturn(professor);


        Professor updateProfessor = professorService.updateProffesor(id,professor);
        Assertions.assertThat(updateProfessor).isNotNull();
        Assertions.assertThat(updateProfessor.getName()).isEqualTo("test");
    }

    @Test void partiallyUpdateProfessor_incorrectProfessor_returnException(){
        Professor professor=null;
        assertThatThrownBy(() -> professorService.partiallyUpdateProffesor(1L,professor))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test void partiallyUpdateProfessor_incorrectId_returnException() {
        Long id = null;
        List<Lecture> lectures = new ArrayList<>();
        lectures.add(new Lecture());
        Professor professor = Professor.builder()
                .name("test")
                .surname("test")
                .professorId(1L)
                .lectures(lectures)
                .build();
        assertThatThrownBy(() -> professorService.partiallyUpdateProffesor(id,professor))
                .isInstanceOf(NullPointerException.class);
    }
    @Test
    void findProfessorById_correctId_returnProfessor(){
        Long id = 1L;
        Professor professor = Professor.builder().build();
        when(professorRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(professor));
        Optional<Professor> foundProfessors = professorService.findProfessorById(id);
        Assertions.assertThat(foundProfessors).isNotNull();
    }
    @Test
    void findProfessorById_idLessThanExpected_returnException(){
        Long id = 0L;
        assertThatThrownBy(() -> professorService.findProfessorById(id))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    void findProfessorById_idIsNull_returnException(){
        Long id =null;
        assertThatThrownBy(() -> professorService.findProfessorById(id))
                .isInstanceOf(NullPointerException.class);
    }
    @Test
    public void deleteProfessorById_correctId_deleteProfessor(){
        Professor professor = Professor.builder().professorId(1L).build();
        when (professorRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.ofNullable(professor));
        assertAll(() -> professorService.deleteProffesor(professor.getProfessorId()));
    }

    @Test
    public void deleteProfessorById_idIsNull_returnException(){
        Professor professor = Professor.builder().professorId(null).build();
        assertThatThrownBy(() ->  professorService.deleteProffesor(professor.getProfessorId()))
                .isInstanceOf(NullPointerException.class);
    }
    @Test
    public void deleteProfessorById_idIsLessThanExpected_returnException(){
        Professor professor = Professor.builder().professorId(0L).build();
        assertThatThrownBy(() ->  professorService.deleteProffesor(professor.getProfessorId()))
                .isInstanceOf(IllegalArgumentException.class);
    }


    }
