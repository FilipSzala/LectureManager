package com.filipszala.lecturemanager;

import com.filipszala.lecturemanager.model.Lecture;
import com.filipszala.lecturemanager.model.Professor;
import com.filipszala.lecturemanager.model.Student;
import com.filipszala.lecturemanager.repository.LectureRepository;
import com.filipszala.lecturemanager.repository.ProfessorRepository;
import com.filipszala.lecturemanager.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbInit implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final LectureRepository lectureRepository;
    @Autowired
    public DbInit(StudentRepository studentRepository, ProfessorRepository professorRepository, LectureRepository lectureRepository) {
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
        this.lectureRepository = lectureRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        /*studentRepository.save(new Student("Filip","Szala"));
        professorRepository.save(new Professor("Marek","Mostowiak"));
        *//*lectureRepository.save(new Lecture("Math","lecture hall 10"));*/
    }
}
