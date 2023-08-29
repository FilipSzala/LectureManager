package com.filipszala.lecturemanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@Table(name="lectures")
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {
    @GeneratedValue
    @Id
    private Long id;
    private Long professorId;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name = "student_lecture",
            joinColumns = @JoinColumn(name = "lecture_id"),
            inverseJoinColumns = @JoinColumn(name ="student_id"))
    @JsonIgnore

    private List <Student> students = new ArrayList<>();
    private String name;
    private String place;

    public Lecture(String name, String place) {
        this.name = name;
        this.place = place;
    }

    public void addStudent(Student student){
        this.students.add(student);
        student.getLectures().add(this);
    }
}


