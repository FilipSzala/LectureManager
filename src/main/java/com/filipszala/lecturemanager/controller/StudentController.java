package com.filipszala.lecturemanager.controller;

import com.filipszala.lecturemanager.model.Professor;
import com.filipszala.lecturemanager.model.Student;
import com.filipszala.lecturemanager.model.User;
import com.filipszala.lecturemanager.repository.StudentRepository;
import com.filipszala.lecturemanager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    public ResponseEntity<List<Student>> displayAllStudent(){
        List <Student> foundUser = studentService.findAllStudents();
        return new ResponseEntity<>(foundUser, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> displayStudentById(@PathVariable("id")Long id){
        User user =studentService.findStudentById(id).orElseThrow();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PostMapping("")
    public ResponseEntity<User> createStudent(@RequestBody Student student){
        User user = studentService.save(student);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<User> updateStudent(@PathVariable("id")Long id,@RequestBody Student updatedStudent){
        User user = studentService.updateStudent(id,updatedStudent);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> partiallyUpdateStudent(@PathVariable("id")Long id,@RequestBody Student updatedStudent){
        User user = studentService.partiallyUpdateStudent(id,updatedStudent);
        return  new ResponseEntity<>(user,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id")Long id){
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
