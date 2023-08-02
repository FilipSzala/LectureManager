package com.filipszala.lecturemanager;

import com.filipszala.lecturemanager.model.Lecturer;
import com.filipszala.lecturemanager.model.Student;
import com.filipszala.lecturemanager.repository.LecturerRepository;
import com.filipszala.lecturemanager.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbInit implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final LecturerRepository lecturerRepository;
    @Autowired
    public DbInit(StudentRepository studentRepository,LecturerRepository lecturerRepository) {
        this.studentRepository = studentRepository;
        this.lecturerRepository = lecturerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        studentRepository.save(new Student("Filip","Szala"));
        lecturerRepository.save(new Lecturer("Roksana","Kolacz"));
    }
}
