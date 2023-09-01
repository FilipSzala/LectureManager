package com.filipszala.lecturemanager.controller.mapper.professor;

import com.filipszala.lecturemanager.controller.dto.lecture.LectureWithoutProfessorAndStudentDto;
import com.filipszala.lecturemanager.controller.dto.professor.ProfessorDto;
import com.filipszala.lecturemanager.model.Lecture;
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
    public static ProfessorDto mapToProfessorDto (Professor professor){
        return ProfessorDto.builder()
                .id(professor.getId())
                .name(professor.getName())
                .surname(professor.getSurname())
                .lectures(mapLectureToDtos(professor))
                .build();
    }
    private static List<LectureWithoutProfessorAndStudentDto> mapLectureToDtos (Professor professor){
        return professor.getLectures().stream()
                .map(lecture -> mapLectureToDto(lecture))
                .collect(Collectors.toList());
    }

    private static LectureWithoutProfessorAndStudentDto mapLectureToDto(Lecture lecture) {
        return LectureWithoutProfessorAndStudentDto.builder()
                .id(lecture.getId())
                .name(lecture.getName())
                .place(lecture.getPlace())
                .build();
    }
}
