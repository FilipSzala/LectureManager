package com.filipszala.lecturemanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Lecturer implements User {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String surname;

    public Lecturer(String name, String surname) {
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
