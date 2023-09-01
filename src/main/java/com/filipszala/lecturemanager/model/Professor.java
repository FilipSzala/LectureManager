package com.filipszala.lecturemanager.model;

import jakarta.persistence.*;
import lombok.*;

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
    private Long id;
    private String name;
    private String surname;
    @OneToMany (mappedBy = "professor")
    private List<Lecture> lectures = new ArrayList<>();
    public Professor(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}
