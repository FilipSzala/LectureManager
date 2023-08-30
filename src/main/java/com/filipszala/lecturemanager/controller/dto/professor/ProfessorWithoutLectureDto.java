package com.filipszala.lecturemanager.controller.dto.professor;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

public class ProfessorWithoutLectureDto {
    private Long id;
    private String name;
    private String surname;
}
