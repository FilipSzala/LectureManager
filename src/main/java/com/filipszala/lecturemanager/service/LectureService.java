package com.filipszala.lecturemanager.service;

import com.filipszala.lecturemanager.model.Lecture;
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
    @Autowired
    public LectureService(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }
    public Lecture save (Lecture lecture){
        return lectureRepository.save(lecture);
    }
    public List<Lecture> findAllLectures (){
        return lectureRepository.findAll();
    }
    public Optional<Lecture> findLectureById(Long id){
        return lectureRepository.findById(id);
    }
    public Lecture findLectureByName(String name){
        List<Lecture> foundLectures = lectureRepository.findLecturesByName(name);
        return foundLectures.get(0);
    }
    public Lecture updateLecture (Long id, Lecture updatedLecture){
        Lecture lecture = findLectureById(id).orElseThrow();
        lecture.setName(updatedLecture.getName());
        lecture.setPlace(updatedLecture.getPlace());
        return save(lecture);
    }
    public Lecture partiallyUpdateLecture (Long id,Lecture updatedLecture){
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
        Lecture lecture = findLectureById(id).orElseThrow();
        lectureRepository.delete(lecture);
    }
}
