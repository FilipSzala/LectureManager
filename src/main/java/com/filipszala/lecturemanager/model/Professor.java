package com.filipszala.lecturemanager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="professors")
@NoArgsConstructor
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
