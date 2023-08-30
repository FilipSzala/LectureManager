package com.filipszala.lecturemanager.controller.dto.professor;

import com.filipszala.lecturemanager.controller.dto.lecture.LectureWithoutProfessorAndStudentDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProfessorDto {
    private Long id;
    private String name;
    private String surname;
    private List<LectureWithoutProfessorAndStudentDto> lectures;
}
