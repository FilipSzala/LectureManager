package com.filipszala.lecturemanager.model;

public class StudentFactory implements UserFactory{
    @Override
    public User createUser(String name, String surname) {
        return new Student(name,surname);
    }
}
