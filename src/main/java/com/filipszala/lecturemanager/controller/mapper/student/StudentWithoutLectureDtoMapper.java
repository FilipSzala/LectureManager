package com.filipszala.lecturemanager.controller.mapper.student;

import com.filipszala.lecturemanager.controller.dto.student.StudentWithoutLectureDto;
import com.filipszala.lecturemanager.model.Student;

import java.util.List;
import java.util.stream.Collectors;

public class StudentWithoutLectureDtoMapper {
    private StudentWithoutLectureDtoMapper() {
    }
    public static List<StudentWithoutLectureDto> mapToStudentDtos (List<Student> students){
        return students.stream()
                .map(student -> mapToStudentDto(student))
                .collect(Collectors.toList());
    }
    private static StudentWithoutLectureDto mapToStudentDto (Student student){
        return StudentWithoutLectureDto.builder()
                .id(student.getId())
                .name(student.getName())
                .surname(student.getSurname())
                .build();
    }
}
