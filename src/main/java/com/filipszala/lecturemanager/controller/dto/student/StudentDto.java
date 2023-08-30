package com.filipszala.lecturemanager.controller.dto.student;

import com.filipszala.lecturemanager.controller.dto.lecture.LectureWithoutProfessorAndStudentDto;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Builder
@Getter
public class StudentDto {
    private Long id;
    private String name;
    private String surname;
    private List<LectureWithoutProfessorAndStudentDto> lectures = new ArrayList<>();
}
