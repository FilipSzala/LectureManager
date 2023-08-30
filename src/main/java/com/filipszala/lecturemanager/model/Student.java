package com.filipszala.lecturemanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student implements User {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String surname;
    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
    private List<Lecture> lectures = new ArrayList<>();

    public Student(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    @Override
    public void displayUser() {

    }

    @Override
    public User deleteUser() {
        return null;
    }

    @Override
    public User updateUser() {
        return null;
    }
}
