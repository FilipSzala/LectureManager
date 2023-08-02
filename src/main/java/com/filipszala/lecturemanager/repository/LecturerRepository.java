package com.filipszala.lecturemanager.repository;



import com.filipszala.lecturemanager.model.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LecturerRepository extends JpaRepository<Lecturer,Long> {
}
