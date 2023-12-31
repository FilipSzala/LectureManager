package com.filipszala.lecturemanager.controller;

import com.filipszala.lecturemanager.controller.dto.professor.ProfessorDto;
import com.filipszala.lecturemanager.controller.dto.professor.ProfessorWithoutLectureDto;
import com.filipszala.lecturemanager.controller.mapper.professor.ProfessorDtoMapper;
import com.filipszala.lecturemanager.controller.mapper.professor.ProfessorWithoutLectureDtoMapper;
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
    public ResponseEntity<List<ProfessorWithoutLectureDto>> displayAllProfessors() {
        List <ProfessorWithoutLectureDto> professorDtos = ProfessorWithoutLectureDtoMapper.mapToProfessorWithoutLectureDtos(professorService.findAllProfessors());
        return new ResponseEntity<>(professorDtos,HttpStatus.OK);
    }
    @GetMapping("/lectures")
    public  ResponseEntity<List<ProfessorDto>> displayAllProfessorsWithLectures() {
        List <ProfessorDto> professorDtos = ProfessorDtoMapper.mapToProfessorDtos(professorService.findAllProfessors());
        return  new ResponseEntity<>(professorDtos,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDto> displayProfessorById(@PathVariable("id") Long id) {
        ProfessorDto professorDto =ProfessorDtoMapper.mapToProfessorDto(professorService.findProfessorById(id).orElseThrow());
        return new ResponseEntity<>(professorDto, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ProfessorDto> createProfessor(@RequestBody Professor professor) {
        ProfessorDto professorDto =ProfessorDtoMapper.mapToProfessorDto(professorService.save(professor));
        return new ResponseEntity<>(professorDto,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorDto> updateProfessor(@PathVariable("id") Long id, @RequestBody Professor updatedProfessor) {
        ProfessorDto professorDto =ProfessorDtoMapper.mapToProfessorDto(professorService.updateProffesor(id, updatedProfessor));
        return new ResponseEntity<>(professorDto,HttpStatus.OK);
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

