package com.filipszala.lecturemanager.service;

import com.filipszala.lecturemanager.model.Student;
import com.filipszala.lecturemanager.repository.StudentRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Getter
@Setter
@Service
public class StudentService {
    private StudentRepository studentRepository;
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public StudentService() {
    }
    public Student save(Student student){
        return studentRepository.save(student);
    }
    public Optional<Student> findStudentById(Long id){
        return studentRepository.findById(id);
    }
    public Student updateStudent(Long id,Student updatedStudent){
        Student student =findStudentById(id).orElseThrow();
        student.setName(updatedStudent.getName());
        student.setSurname(updatedStudent.getSurname());
        return save(student);
    }
    public Student partiallyUpdateStudent(Long id,Student updatedStudent){
        Student student =findStudentById(id).orElseThrow();
        if(updatedStudent.getName()!=null){
            student.setName(updatedStudent.getName());
        }
        if(updatedStudent.getSurname() != null){
            student.setSurname(updatedStudent.getSurname());
        }
        return save(student);
    }

    public void deleteStudent(Long id){
        Student student = findStudentById(id).orElseThrow();
        studentRepository.delete(student);
    }
}
