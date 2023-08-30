package com.filipszala.lecturemanager.controller.dto.lecture;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LectureWithoutProfessorAndStudentDto {
    private Long id;
    private String name;
    private String place;
}
