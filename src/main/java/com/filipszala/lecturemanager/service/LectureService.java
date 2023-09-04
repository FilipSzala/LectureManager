package com.filipszala.lecturemanager.service;

import com.filipszala.lecturemanager.model.Lecture;
import com.filipszala.lecturemanager.model.Professor;
import com.filipszala.lecturemanager.repository.LectureRepository;
import com.filipszala.lecturemanager.repository.ProfessorRepository;
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
public class LectureService {
    private LectureRepository lectureRepository;
    private StudentRepository studentRepository;
    private ProfessorRepository professorRepository;

    @Autowired
    public LectureService(LectureRepository lectureRepository, StudentRepository studentRepository,ProfessorRepository professorRepository) {
        this.lectureRepository = lectureRepository;
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
    }

    public Lecture save (Lecture lecture){
        if (lecture.getName() == null || lecture.getPlace() == null) {
            throw new IllegalArgumentException("Fields of lecture can't be empty");
        } else if (lecture == null) {
            throw new IllegalArgumentException("Lecture can't be empty");
        }
        return lectureRepository.save(lecture);
    }

    public void addProfessorToLecture(Long id, Lecture lecture) {
        Professor professor = professorRepository.findById(id).orElseThrow();
        lecture.setProfessor(professor);
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
    public Lecture updateLecture (Long id, Lecture updatedLecture){
        if (updatedLecture==null){
            throw new NullPointerException("Updated lecture can't be null");
        }
        if (updatedLecture.getPlace()==null||updatedLecture.getStudents()==null||/*updatedLecture.getProfessorId()==null||*/updatedLecture.getName()==null){
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
        if(updatedLecture.getStudents().size()>0){
            lecture.setStudents(updatedLecture.getStudents());
        }
        if (updatedLecture.getProfessor()!=null){
            lecture.setProfessor(updatedLecture.getProfessor());
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

}
