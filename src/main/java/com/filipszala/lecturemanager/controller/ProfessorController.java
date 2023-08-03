package com.filipszala.lecturemanager.controller;

import com.filipszala.lecturemanager.model.Professor;
import com.filipszala.lecturemanager.model.User;
import com.filipszala.lecturemanager.repository.LectureRepository;
import com.filipszala.lecturemanager.repository.ProfessorRepository;
import com.filipszala.lecturemanager.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professors")
public class ProfessorController {
    private ProfessorRepository professorRepository;
    private ProfessorService professorService;
    private LectureRepository lectureRepository;
    @Autowired
    public ProfessorController(ProfessorRepository professorRepository, ProfessorService professorService, LectureRepository lectureRepository) {
        this.professorRepository = professorRepository;
        this.professorService = professorService;
        this.lectureRepository = lectureRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<Professor>> displayAllProfessor() {
        List <Professor> foundUser  = professorService.findAllProfessors();
        return new ResponseEntity<>(foundUser,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> displayProfessorById(@PathVariable("id") Long id) {
        User user =professorService.findProfessorById(id).orElseThrow();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<User> createProfessor(@RequestBody Professor professor) {
        User user =professorService.save(professor);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateProfessor(@PathVariable("id") Long id, @RequestBody Professor updatedProfessor) {
        User user =professorService.updateProffesor(id, updatedProfessor);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> partiallyUpdateProfessor(@PathVariable("id") Long id, @RequestBody Professor updatedProfessor) {
        User user = professorService.partiallyUpdateProffesor(id, updatedProfessor);
        return  new ResponseEntity<>(user,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable("id") Long id) {
        professorService.deleteProffesor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

