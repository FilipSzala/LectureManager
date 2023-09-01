package com.filipszala.lecturemanager.service;

import com.filipszala.lecturemanager.model.Lecture;
import com.filipszala.lecturemanager.model.Student;
import com.filipszala.lecturemanager.repository.LectureRepository;
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
    private LectureService lectureService;
    @Autowired
    public StudentService(StudentRepository studentRepository,LectureService lectureService) {
        this.studentRepository = studentRepository;
        this.lectureService = lectureService;
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
    public void selectLecture(Long lectureId, Long studentId){
        if (lectureId==null){
            throw new NullPointerException("Id can't be null");
        }if (lectureId<=0) {
            throw new IllegalArgumentException("Id can't be less than 1");
        }
        if (studentId==null){
            throw new NullPointerException("Id can't be null");
        }else if (studentId<=0) {
            throw new IllegalArgumentException("Id can't be less than 1");
        }
        Lecture lecture =lectureService.findLectureById(lectureId).orElseThrow();
        Student student = findStudentById(studentId).orElseThrow();
        lecture.addStudent(student);
        //cascade in class Lecture allows us to add single object from
        //relationship to the database, another one will be automatically added.
        lectureService.partiallyUpdateLecture(lecture.getId(),lecture);
    }
}
