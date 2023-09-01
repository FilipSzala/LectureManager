package com.filipszala.lecturemanager.controller.mapper.lecture;

import com.filipszala.lecturemanager.controller.dto.lecture.LectureDto;
import com.filipszala.lecturemanager.controller.dto.professor.ProfessorWithoutLectureDto;
import com.filipszala.lecturemanager.controller.mapper.student.StudentWithoutLectureDtoMapper;
import com.filipszala.lecturemanager.model.Lecture;
import com.filipszala.lecturemanager.model.Professor;

import java.util.List;
import java.util.stream.Collectors;

public class LectureDtoMapper {
    private LectureDtoMapper() {
    }
    public static List<LectureDto> mapToLectureDtos (List<Lecture>lectures){
        return   lectures.stream()
                .map(lecture -> mapToLectureDto(lecture))
                .collect(Collectors.toList());
    }
    public static LectureDto mapToLectureDto (Lecture lecture){
        return LectureDto.builder()
                .id(lecture.getId())
                .name(lecture.getName())
                .place(lecture.getPlace())
                .professorWithoutLectureDto(mapProfessorToDto(lecture.getProfessor()))
                .studentWithoutLectureDtos(StudentWithoutLectureDtoMapper.mapToStudentDtos(lecture.getStudents()))
                .build();
    }
    private static ProfessorWithoutLectureDto mapProfessorToDto(Professor professor){
        return ProfessorWithoutLectureDto.builder()
                .id(professor.getId())
                .name(professor.getName())
                .surname(professor.getSurname())
                .build();
    }

}
