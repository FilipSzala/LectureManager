package com.filipszala.lecturemanager.service;

import com.filipszala.lecturemanager.model.Student;
import com.filipszala.lecturemanager.repository.StudentRepository;
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
public class StudentService {
    private StudentRepository studentRepository;
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public Student save(Student student){
        if (student.getName() == null || student.getSurname() == null) {
            throw new IllegalArgumentException("Fields of student can't be empty");
        } else if (student == null) {
            throw new IllegalArgumentException("Student can't be empty");
        }
        return studentRepository.save(student);
    }

    public List<Student> findAllStudents(){
        return studentRepository.findAll();
    }


    public Optional<Student> findStudentById(Long id){
        if (id==null){
            throw new NullPointerException("Id can't be null");
        }else if (id<=0) {
            throw new IllegalArgumentException("Id can't be less than 1");
        }
        return studentRepository.findById(id);
    }
    public Student updateStudent(Long id,Student updatedStudent){
        if (updatedStudent==null){
            throw new IllegalArgumentException("Updated student can't be empty");
        }
        if (updatedStudent.getSurname()==null||updatedStudent.getName()==null||updatedStudent.getId()==null||updatedStudent.getLectures().isEmpty()){
            throw new IllegalArgumentException("Fields of student can't be empty");
        }
        else if (id==null){
            throw new NullPointerException("Id can't be null");
        }else if (id<=0) {
            throw new IllegalArgumentException("Id can't be less than 1");
        }
        Student student =findStudentById(id).orElseThrow();
        student.setName(updatedStudent.getName());
        student.setSurname(updatedStudent.getSurname());
        return save(student);
    }
    public Student partiallyUpdateStudent(Long id,Student updatedStudent){
        if (updatedStudent==null){
            throw new IllegalArgumentException("Updated student can't be empty");
        }
        else if (id==null){
            throw new NullPointerException("Id can't be null");
        }else if (id<=0) {
            throw new IllegalArgumentException("Id can't be less than 1");
        }
        Student student =findStudentById(id).orElseThrow();
        if(updatedStudent.getLectures()!=null){
            student.setLectures(updatedStudent.getLectures());
        }
        if(updatedStudent.getName()!=null){
            student.setName(updatedStudent.getName());
        }
        if(updatedStudent.getSurname() != null){
            student.setSurname(updatedStudent.getSurname());
        }
        return save(student);
    }

    public void deleteStudent(Long id){
         if (id==null){
            throw new NullPointerException("Id can't be null");
        }else if (id<=0) {
            throw new IllegalArgumentException("Id can't be less than 1");
        }
        Student student = findStudentById(id).orElseThrow();
        studentRepository.delete(student);
    }
}
