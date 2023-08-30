package com.filipszala.lecturemanager.controller.mapper.lecture;

import com.filipszala.lecturemanager.controller.dto.lecture.LectureWithoutProfessorAndStudentDto;
import com.filipszala.lecturemanager.model.Lecture;

import java.util.List;
import java.util.stream.Collectors;

public class LectureWithoutProfessorAndStudentDtoMapper {
    public LectureWithoutProfessorAndStudentDtoMapper() {
    }

    public static List<LectureWithoutProfessorAndStudentDto> mapToLectureDtos (List <Lecture> lectures){
        return lectures.stream()
                .map(lecture -> mapToLectureDto(lecture))
                .collect(Collectors.toList());
    }
    private static LectureWithoutProfessorAndStudentDto mapToLectureDto (Lecture lecture){
        return LectureWithoutProfessorAndStudentDto.builder()
                .id(lecture.getId())
                .name(lecture.getName())
                .place(lecture.getPlace())
                .build();
    }
}
