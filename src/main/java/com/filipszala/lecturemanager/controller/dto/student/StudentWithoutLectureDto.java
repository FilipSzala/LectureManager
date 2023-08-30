package com.filipszala.lecturemanager.controller.dto.student;

import lombok.Builder;
import lombok.Getter;
@Getter
@Builder

public class StudentWithoutLectureDto {
    private Long id;
    private String name;
    private String surname;

}
