package com.filipszala.lecturemanager.dto.lecture;

import com.filipszala.lecturemanager.model.Lecture;

import java.util.List;
import java.util.stream.Collectors;

public class LectureDtoMapper {
    public LectureDtoMapper() {
    }

    public static List<LectureDto> mapToLectureDtos (List <Lecture> lectures){
        return lectures.stream()
                .map(lecture -> mapToLectureDto(lecture))
                .collect(Collectors.toList());
    }
    private static LectureDto mapToLectureDto (Lecture lecture){
        return LectureDto.builder()
                .id(lecture.getId())
                .professorId(lecture.getProfessorId())
                .name(lecture.getName())
                .place(lecture.getPlace())
                .build();
    }
}
