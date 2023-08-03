package com.filipszala.lecturemanager.service;

import com.filipszala.lecturemanager.model.Lecture;
import com.filipszala.lecturemanager.model.Student;
import com.filipszala.lecturemanager.repository.LectureRepository;
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
public class LectureService {
    private LectureRepository lectureRepository;
    private StudentService studentService;
    @Autowired
    public LectureService(LectureRepository lectureRepository,StudentService studentService) {
        this.lectureRepository = lectureRepository;
        this.studentService = studentService;
    }
    public Lecture save (Lecture lecture){
        if (lecture.getName() == null || lecture.getPlace() == null) {
            throw new IllegalArgumentException("Fields of lecture can't be empty");
        } else if (lecture == null) {
            throw new IllegalArgumentException("Lecture can't be empty");
        }
        return lectureRepository.save(lecture);
    }
    public List<Lecture> findAllLectures (){
        return lectureRepository.findAll();
    }
    public Optional<Lecture> findLectureById(Long id){
        if (id==null){
            throw new NullPointerException("Id can't be null");
        }else if (id<=0) {
            throw new IllegalArgumentException("Id can't be less than 1");
        }
        return lectureRepository.findById(id);
    }
    public Lecture findLectureByName(String name){
        if(name==null){
            throw new NullPointerException("Name can't be null");
        }
        if (name.isEmpty()){
            throw new IllegalArgumentException("Name can't be empty");
        }
        List<Lecture> foundLectures = lectureRepository.findLecturesByName(name);
        return foundLectures.get(0);
    }
    public Lecture updateLecture (Long id, Lecture updatedLecture){
        if (updatedLecture==null){
            throw new NullPointerException("Updated lecture can't be null");
        }
        if (updatedLecture.getPlace()==null||updatedLecture.getStudentsList()==null||updatedLecture.getProfessorId()==null||updatedLecture.getName()==null){
            throw new IllegalArgumentException("Fields of lecture can't be empty");
        }
        else if (id==null){
            throw new NullPointerException("Id can't be null");
        }else if (id<=0) {
            throw new IllegalArgumentException("Id can't be less than 1");
        }
        Lecture lecture = findLectureById(id).orElseThrow();
        lecture.setName(updatedLecture.getName());
        lecture.setPlace(updatedLecture.getPlace());
        return save(lecture);
    }
    public Lecture partiallyUpdateLecture (Long id,Lecture updatedLecture){
        if (updatedLecture==null){
            throw new NullPointerException("Updated lecture can't be null");
        }
        else if (id==null){
            throw new NullPointerException("Id can't be null");
        }else if (id<=0) {
            throw new IllegalArgumentException("Id can't be less than 1");
        }
        Lecture lecture = findLectureById(id).orElseThrow();
        if(updatedLecture.getStudentsList()!=null){
            lecture.setStudentsList(updatedLecture.getStudentsList());
        }
        if (updatedLecture.getProfessorId()!=null){
            lecture.setProfessorId(updatedLecture.getProfessorId());
        }
        if(updatedLecture.getName()!=null) {
            lecture.setName(updatedLecture.getName());
        }
        if(updatedLecture.getPlace()!=null) {
            lecture.setPlace(updatedLecture.getPlace());
        }
        return save(lecture);
    }
    public void deleteLecture (Long id){
        if (id==null){
            throw new NullPointerException("Id can't be null");
        }else if (id<=0) {
            throw new IllegalArgumentException("Id can't be less than 1");
        }
        Lecture lecture = findLectureById(id).orElseThrow();
        lectureRepository.delete(lecture);
    }
    public void selectLecture(String lectureName, Long studentId){
        if(lectureName==null){
            throw new NullPointerException("Name can't be null");
        }
        if (lectureName.isEmpty()){
            throw new IllegalArgumentException("Name can't be empty");
        }
        if (studentId==null){
            throw new NullPointerException("Id can't be null");
        }else if (studentId<=0) {
            throw new IllegalArgumentException("Id can't be less than 1");
        }
        Lecture lecture =findLectureByName(lectureName);
        Student student = studentService.findStudentById(studentId).orElseThrow();
        lecture.getStudentsList().add(student);
        student.getSelectedLectures().add(lecture);
        studentService.partiallyUpdateStudent(studentId,student);
        partiallyUpdateLecture(lecture.getId(),lecture);
    }
}
