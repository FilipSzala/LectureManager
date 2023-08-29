package com.filipszala.lecturemanager.dto.professor;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfessorDto {
    private Long professorId;
    private String name;
    private String surname;
}
