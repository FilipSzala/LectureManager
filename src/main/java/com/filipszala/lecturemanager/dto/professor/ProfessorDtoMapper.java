package com.filipszala.lecturemanager.dto.professor;

import com.filipszala.lecturemanager.model.Professor;

import java.util.List;
import java.util.stream.Collectors;

public class ProfessorDtoMapper {
    private ProfessorDtoMapper() {
    }

    public static List<ProfessorDto> mapToProfessorDtos (List<Professor> professors){
        return professors.stream()
                .map(professor -> mapToProfessorDto(professor))
                .collect(Collectors.toList());
    }
    private static ProfessorDto mapToProfessorDto (Professor professor){
        return ProfessorDto.builder()
                .professorId(professor.getProfessorId())
                .name(professor.getName())
                .surname(professor.getSurname())
                .build();
    }
}
