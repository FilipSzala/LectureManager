package com.filipszala.lecturemanager.service;

import com.filipszala.lecturemanager.model.Lecture;
import com.filipszala.lecturemanager.model.Professor;
import com.filipszala.lecturemanager.repository.LectureRepository;
import com.filipszala.lecturemanager.repository.ProfessorRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@Service
@NoArgsConstructor
public class ProfessorService {
    private ProfessorRepository professorRepository;
    private LectureRepository lectureRepository;
    @Autowired
    public ProfessorService(ProfessorRepository professorRepository,LectureRepository lectureRepository) {
        this.professorRepository = professorRepository;
        this.lectureRepository = lectureRepository;
    }
    public Professor save(Professor professor){
        if (professor.getName() == null || professor.getSurname() == null) {
            throw new IllegalArgumentException("Fields of professor can't be empty");
        } else if (professor == null) {
            throw new IllegalArgumentException("Professor can't be empty");
        }
        return professorRepository.save(professor);
    }
    public List<Professor> findAllProfessors(){
        return professorRepository.findAll();
    }
    public Optional<Professor> findProfessorById (Long id){
        if (id==null){
            throw new NullPointerException("Id can't be null");
        }else if (id<=0) {
            throw new IllegalArgumentException("Id can't be less than 1");
        }
        return professorRepository.findById(id);
    }
    public Professor updateProffesor (Long id,Professor updatedProfessor){
        if (updatedProfessor==null){
            throw new IllegalArgumentException("Updated professor can't be empty");
        }
        if (updatedProfessor.getSurname()==null||updatedProfessor.getName()==null||updatedProfessor.getId()==null){
            throw new IllegalArgumentException("Fields of professor can't be empty");
        }
        else if (id==null){
            throw new NullPointerException("Id can't be null");
        }else if (id<=0) {
            throw new IllegalArgumentException("Id can't be less than 1");
        }
        Professor professor = findProfessorById(id).orElseThrow();
        professor.setName(updatedProfessor.getName());
        professor.setSurname(updatedProfessor.getSurname());
        professor.setLectures(updatedProfessor.getLectures());
        return save(professor);
    }
    public Professor partiallyUpdateProffesor (Long id,Professor updatedProfessor){
        if (updatedProfessor==null){
            throw new IllegalArgumentException("Updated professor can't be empty");
        }
        else if (id==null){
            throw new NullPointerException("Id can't be null");
        }else if (id<=0) {
            throw new IllegalArgumentException("Id can't be less than 1");
        }
        Professor professor = findProfessorById(id).orElseThrow();
        if(updatedProfessor.getName()!=null) {
            professor.setName(updatedProfessor.getName());
        }
        if (updatedProfessor.getSurname()!=null) {
            professor.setSurname(updatedProfessor.getSurname());
        }
        if (updatedProfessor.getLectures()!=null) {
            professor.setLectures(updatedProfessor.getLectures());
        }
        return save(professor);
    }
    public void deleteProffesor (Long id){
        if (id==null){
            throw new NullPointerException("Id can't be null");
        }else if (id<=0) {
            throw new IllegalArgumentException("Id can't be less than 1");
        }
        Professor professor = findProfessorById(id).orElseThrow();
        professorRepository.delete(professor);
    }
}


