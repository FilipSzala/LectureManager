package com.filipszala.lecturemanager.service;

import com.filipszala.lecturemanager.model.Professor;
import com.filipszala.lecturemanager.repository.ProfessorRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Service
@NoArgsConstructor
public class ProfessorService {
    private ProfessorRepository professorRepository;
    @Autowired
    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }
    public Professor save(Professor professor){
        return professorRepository.save(professor);
    }
    public List<Professor> findAllProfessors(){
        return professorRepository.findAll();
    }
    public Optional<Professor> findProfessorById (Long id){
        return professorRepository.findById(id);
    }
    public Professor updateProffesor (Long id,Professor updatedProffesor){
        Professor professor = findProfessorById(id).orElseThrow();
        professor.setName(updatedProffesor.getName());
        professor.setSurname(updatedProffesor.getSurname());
        professor.setLectures(updatedProffesor.getLectures());
        return save(professor);
    }
    public Professor partiallyUpdateProffesor (Long id,Professor updatedProffesor){
        Professor professor = findProfessorById(id).orElseThrow();
        if(updatedProffesor.getName()!=null) {
            professor.setName(updatedProffesor.getName());
        }
        if (updatedProffesor.getSurname()!=null) {
            professor.setSurname(updatedProffesor.getSurname());
        }
        if (updatedProffesor.getLectures()!=null) {
            professor.setLectures(updatedProffesor.getLectures());
        }
        return save(professor);
    }
    public void deleteProffesor (Long id){
        Professor professor = findProfessorById(id).orElseThrow();
        professorRepository.delete(professor);
    }
}


