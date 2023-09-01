package com.filipszala.lecturemanager.controller.mapper.student;

import com.filipszala.lecturemanager.controller.dto.lecture.LectureWithoutProfessorAndStudentDto;
import com.filipszala.lecturemanager.controller.dto.student.StudentDto;
import com.filipszala.lecturemanager.controller.mapper.lecture.LectureWithoutProfessorAndStudentDtoMapper;
import com.filipszala.lecturemanager.model.Student;

import java.util.List;
import java.util.stream.Collectors;

public class StudentDtoMapper {
    private StudentDtoMapper() {
    }
    public static List<StudentDto> mapToStudentDtos(List<Student> students){
        return students.stream()
                .map(student -> mapToStudentDto(student))
                .collect(Collectors.toList());
    }
    public static StudentDto mapToStudentDto (Student student){
        List <LectureWithoutProfessorAndStudentDto> x =LectureWithoutProfessorAndStudentDtoMapper.mapToLectureDtos(student.getLectures());
        return StudentDto.builder()
                .id(student.getId())
                .name(student.getName())
                .surname(student.getSurname())
                .lectures(LectureWithoutProfessorAndStudentDtoMapper.mapToLectureDtos(student.getLectures()))
                .build();
    }
}
