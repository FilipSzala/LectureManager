package com.filipszala.lecturemanager.controller;

import com.filipszala.lecturemanager.model.Student;
import com.filipszala.lecturemanager.model.User;
import com.filipszala.lecturemanager.repository.StudentRepository;
import com.filipszala.lecturemanager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
    private StudentRepository studentRepository;
    private StudentService studentService;
    @Autowired
    public StudentController(StudentRepository studentRepository,StudentService studentService) {

        this.studentRepository = studentRepository;
        this.studentService = studentService;
    }
    @GetMapping("")
    @ResponseBody
    public List<Student> displayAllStudent(){
        return studentService.findAllStudents();
    }
    @GetMapping("/{id}")
    @ResponseBody
    public User displayStudentById(@PathVariable("id")Long id){
        return studentService.findStudentById(id).orElseThrow();
    }
    @PostMapping("")
    @ResponseBody
    public User createStudent(@RequestBody Student student){
        return studentService.save(student);
    }
    @PutMapping("/{id}")
    @ResponseBody
    public User updateStudent(@PathVariable("id")Long id,@RequestBody Student updatedStudent){
        return studentService.updateStudent(id,updatedStudent);
    }

    @PatchMapping("/{id}")
    @ResponseBody
    public User partiallyUpdateStudent(@PathVariable("id")Long id,@RequestBody Student updatedStudent){
        return studentService.partiallyUpdateStudent(id,updatedStudent);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteStudent(@PathVariable("id")Long id){
        studentService.deleteStudent(id);
    }
}
