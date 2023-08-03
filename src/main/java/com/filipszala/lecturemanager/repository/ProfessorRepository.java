package com.filipszala.lecturemanager.repository;



import com.filipszala.lecturemanager.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor,Long> {
}
