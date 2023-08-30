package com.filipszala.lecturemanager.service;

import com.filipszala.lecturemanager.model.Lecture;
import com.filipszala.lecturemanager.model.Professor;
import com.filipszala.lecturemanager.model.Student;
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
import java.util.stream.Collectors;

@Getter
@Setter
@Service
@NoArgsConstructor
public class LectureService {
    private LectureRepository lectureRepository;
    private StudentService studentService;
    private StudentRepository studentRepository;
    private ProfessorRepository professorRepository;

    @Autowired
    public LectureService(LectureRepository lectureRepository, StudentService studentService, StudentRepository studentRepository,ProfessorRepository professorRepository) {
        this.lectureRepository = lectureRepository;
        this.studentService = studentService;
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
    public List<Lecture> findAllLectures (){
        return lectureRepository.findAll();
    }
    public List<Lecture> findAllLecturesWithProfessor (){
        List <Lecture> lectures = lectureRepository.findAll();
        lectures.forEach(lecture -> lecture.setProfessor(professorRepository.findById(lecture.getProfessor().getId()).orElseThrow()));
        return lectures;
    }
    private List<Professor> extractProfessors (List<Professor> professors, long id){
        return professors.stream()
                .filter(professor -> professor.getId() == id)
                .collect(Collectors.toList());
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
        if(updatedLecture.getStudents()!=null){
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
        Lecture lecture =findLectureById(lectureId).orElseThrow();
        Student student = studentService.findStudentById(studentId).orElseThrow();
        lecture.addStudent(student);
        //cascade in class Lecture allows us to add single object from
        //relationship to the database, another one will be automatically added.
        partiallyUpdateLecture(lecture.getId(),lecture);
    }
}
