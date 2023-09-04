package com.filipszala.lecturemanager.repository;

import com.filipszala.lecturemanager.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture,Long>{
    List<Lecture> findAllByProfessorIdIn(List<Long> ids);
}
