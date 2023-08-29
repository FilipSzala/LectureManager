package com.filipszala.lecturemanager.dto.lecture;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LectureDto {
    private Long id;
    private Long professorId;
    private String name;
    private String place;
}
