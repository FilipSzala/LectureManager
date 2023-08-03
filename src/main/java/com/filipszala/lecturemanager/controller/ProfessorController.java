package com.filipszala.lecturemanager.controller;

import com.filipszala.lecturemanager.model.Lecture;
import com.filipszala.lecturemanager.model.Professor;
import com.filipszala.lecturemanager.model.User;
import com.filipszala.lecturemanager.repository.LectureRepository;
import com.filipszala.lecturemanager.repository.ProfessorRepository;
import com.filipszala.lecturemanager.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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
    @ResponseBody
    public List<Professor> displayAllProfessor() {
        return professorService.findAllProfessors();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public User displayProfessorById(@PathVariable("id") Long id) {
        return professorService.findProfessorById(id).orElseThrow();
    }

    @PostMapping("")
    @ResponseBody
    public User createProfessor(@RequestBody Professor professor) {
        return professorService.save(professor);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public User updateProfessor(@PathVariable("id") Long id, @RequestBody Professor updatedProfessor) {
        return professorService.updateProffesor(id, updatedProfessor);
    }

    @PatchMapping("/{id}")
    @ResponseBody
    public User partiallyUpdateProfessor(@PathVariable("id") Long id, @RequestBody Professor updatedProfessor) {
        return professorService.partiallyUpdateProffesor(id, updatedProfessor);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteProfessor(@PathVariable("id") Long id) {
        professorService.deleteProffesor(id);
    }

}

