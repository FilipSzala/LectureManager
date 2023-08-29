package com.filipszala.lecturemanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@Table(name="professors")
@NoArgsConstructor
@AllArgsConstructor
public class Professor implements User {
    @Id
    @GeneratedValue
    private Long professorId;
    private String name;
    private String surname;
    @OneToMany
    @JoinColumn(name="professorId")
    private List<Lecture> lectures = new ArrayList<>();

    public Professor(String name, String surname) {
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
