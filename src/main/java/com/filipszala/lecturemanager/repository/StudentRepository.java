package com.filipszala.lecturemanager.repository;

import com.filipszala.lecturemanager.model.Lecture;
import com.filipszala.lecturemanager.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
