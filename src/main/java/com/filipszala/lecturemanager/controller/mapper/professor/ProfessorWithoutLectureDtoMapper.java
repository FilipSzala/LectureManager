package com.filipszala.lecturemanager.controller.mapper.professor;

import com.filipszala.lecturemanager.controller.dto.professor.ProfessorWithoutLectureDto;
import com.filipszala.lecturemanager.model.Professor;

import java.util.List;
import java.util.stream.Collectors;

public class ProfessorWithoutLectureDtoMapper {
    private ProfessorWithoutLectureDtoMapper() {
    }

    public static List<ProfessorWithoutLectureDto> mapToProfessorWithoutLectureDtos (List<Professor> professors){
        return professors.stream()
                .map(professor -> mapToProfessorWithoutLectureDto(professor))
                .collect(Collectors.toList());
    }
    private static ProfessorWithoutLectureDto mapToProfessorWithoutLectureDto (Professor professor){
        return ProfessorWithoutLectureDto.builder()
                .id(professor.getId())
                .name(professor.getName())
                .surname(professor.getSurname())
                .build();
    }
}
