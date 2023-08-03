package com.filipszala.lecturemanager;

import com.filipszala.lecturemanager.model.Professor;
import com.filipszala.lecturemanager.model.Student;
import com.filipszala.lecturemanager.repository.ProfessorRepository;
import com.filipszala.lecturemanager.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbInit implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    @Autowired
    public DbInit(StudentRepository studentRepository, ProfessorRepository professorRepository) {
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        studentRepository.save(new Student("Filip","Szala"));
        professorRepository.save(new Professor("Roksana","Kolacz"));
    }
}
