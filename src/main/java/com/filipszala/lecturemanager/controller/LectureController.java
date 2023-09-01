package com.filipszala.lecturemanager.controller;

import com.filipszala.lecturemanager.controller.dto.lecture.LectureDto;
import com.filipszala.lecturemanager.controller.dto.lecture.LectureWithoutProfessorAndStudentDto;
import com.filipszala.lecturemanager.controller.mapper.lecture.LectureDtoMapper;
import com.filipszala.lecturemanager.controller.mapper.lecture.LectureWithoutProfessorAndStudentDtoMapper;
import com.filipszala.lecturemanager.model.Lecture;
import com.filipszala.lecturemanager.model.Professor;
import com.filipszala.lecturemanager.service.LectureService;
import com.filipszala.lecturemanager.service.ProfessorService;
import com.filipszala.lecturemanager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lectures")
public class LectureController {

    private LectureService lectureService;
    private StudentService studentService;
    private ProfessorService professorService;
    @Autowired
    public LectureController(LectureService lectureService,StudentService studentService,ProfessorService professorService) {
        this.lectureService = lectureService;
        this.studentService = studentService;
        this.professorService = professorService;
    }
    @GetMapping("")
    public ResponseEntity<List<LectureWithoutProfessorAndStudentDto>> displayAllLectures(){
        List<LectureWithoutProfessorAndStudentDto> lectureDtos = LectureWithoutProfessorAndStudentDtoMapper.mapToLectureDtos(lectureService.findAllLectures());
        return new ResponseEntity<>(lectureDtos, HttpStatus.OK);
    }
    @GetMapping("/professors/students")
    public ResponseEntity<List<LectureDto>> displayAllLecturesWithProfessorsAndStudents(){
        List<LectureDto> lectureDtos =LectureDtoMapper.mapToLectureDtos(lectureService.findAllLectures());
        return new ResponseEntity<>(lectureDtos,HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<LectureDto> displayLectureById(@PathVariable("id") Long id){
        LectureDto lecture = LectureDtoMapper.mapToLectureDto(lectureService.findLectureById(id).orElseThrow());
        return new ResponseEntity<>(lecture, HttpStatus.OK);
    }

    @PostMapping("/professors/{id}")
    public ResponseEntity<Lecture> createLecture (@PathVariable("id")Long id,@RequestBody Lecture lecture){
        Professor professor = professorService.findProfessorById(id).orElseThrow();
        lecture.setProfessor(professor);
        Lecture savedLecture = lectureService.save(lecture);
        return new ResponseEntity<>(/*savedLecture, */HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lecture> updateLecture (@PathVariable("id")Long id,@RequestBody Lecture updatedLecture){
        Lecture lecture = lectureService.updateLecture(id,updatedLecture);
        return new ResponseEntity<>(lecture, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Lecture> partiallyUpdateLecture(@PathVariable("id")Long id, Lecture updatedLecture){
        Lecture lecture =  lectureService.partiallyUpdateLecture(id,updatedLecture);
        return new ResponseEntity<>(lecture, HttpStatus.OK);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteLecture (@PathVariable("id")Long id){
        lectureService.deleteLecture(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/students/{lectureId}/{studentId}")
    public ResponseEntity<String> selectLecture (@PathVariable("lectureId")Long lectureId,@PathVariable("studentId") Long studentId){
        lectureService.selectLecture(lectureId,studentId);
        return ResponseEntity.ok("Lecture selected by the student");
    }
}
