package com.filipszala.lecturemanager.controller.dto.lecture;

import com.filipszala.lecturemanager.controller.dto.professor.ProfessorWithoutLectureDto;
import com.filipszala.lecturemanager.controller.dto.student.StudentWithoutLectureDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class LectureDto {
    private Long id;
    private ProfessorWithoutLectureDto professorWithoutLectureDto;
    private List<StudentWithoutLectureDto> studentWithoutLectureDtos;
    private String name;
    private String place;
}
