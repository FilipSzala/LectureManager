package com.filipszala.lecturemanager.controller;

import com.filipszala.lecturemanager.model.Lecture;
import com.filipszala.lecturemanager.service.LectureService;
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
    @Autowired
    public LectureController(LectureService lectureService,StudentService studentService) {
        this.lectureService = lectureService;
        this.studentService = studentService;
    }
    @GetMapping("")
    public ResponseEntity<List<Lecture>> displayAllLectures(){

        List<Lecture>foundLectures = lectureService.findAllLectures();
        return new ResponseEntity<>(foundLectures, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lecture> displayLectureById(@PathVariable("id") Long id){
        Lecture lecture = lectureService.findLectureById(id).orElseThrow();
        return new ResponseEntity<>(lecture, HttpStatus.OK);
    }

    @PostMapping("/professors/{id}")
    public ResponseEntity<Lecture> createLecture (@PathVariable("id")Long id,@RequestBody Lecture lecture){
        lecture.setProfessorId(id);
        Lecture savedLecture = lectureService.save(lecture);
        return new ResponseEntity<>(savedLecture, HttpStatus.CREATED);
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

    @PatchMapping("/students/{lectureName}/{studentId}")
    public ResponseEntity<String> selectLecture (@PathVariable("lectureName")String lectureName,@PathVariable("studentId") Long studentId){
        lectureService.selectLecture(lectureName,studentId);
        return ResponseEntity.ok("Lecture selected by the student");
    }
}
