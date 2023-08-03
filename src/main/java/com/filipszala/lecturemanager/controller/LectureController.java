package com.filipszala.lecturemanager.controller;

import com.filipszala.lecturemanager.model.Lecture;
import com.filipszala.lecturemanager.model.Student;
import com.filipszala.lecturemanager.repository.LectureRepository;
import com.filipszala.lecturemanager.service.LectureService;
import com.filipszala.lecturemanager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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
    @ResponseBody
    public List<Lecture> displayAllLectures(){
        return lectureService.findAllLectures();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Lecture displayLectureById(@PathVariable("id") Long id){
        return lectureService.findLectureById(id).orElseThrow();
    }

    @PostMapping("/professors/{id}")
    @ResponseBody
    public Lecture createLecture (@PathVariable("id")Long id,@RequestBody Lecture lecture){
        lecture.setProfessorId(id);
        return lectureService.save(lecture);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Lecture updateLecture (@PathVariable("id")Long id,@RequestBody Lecture updatedLecture){
      return lectureService.updateLecture(id,updatedLecture);
    }

    @PatchMapping("/{id}")
    @ResponseBody
    public Lecture partiallyUpdateLecture(@PathVariable("id")Long id, Lecture lecture){
        return lectureService.partiallyUpdateLecture(id,lecture);
    }

    @DeleteMapping ("/{id}")
    @ResponseBody
    public void deleteLecture (@PathVariable("id")Long id){
        lectureService.deleteLecture(id);
    }

    @PatchMapping("/students/{lectureName}/{studentId}")
    @ResponseBody
    public String selectLecture (@PathVariable("lectureName")String lectureName,@PathVariable("studentId") Long studentId){
        Lecture lecture =lectureService.findLectureByName(lectureName);
        Student student = studentService.findStudentById(studentId).orElseThrow();
        lecture.getStudentsList().add(student);
        student.getSelectedLectures().add(lecture);
        studentService.partiallyUpdateStudent(studentId,student);
        lectureService.partiallyUpdateLecture(lecture.getId(),lecture);
        return null;
    }
}
