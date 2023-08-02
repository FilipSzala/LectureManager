package com.filipszala.lecturemanager.model;

public class LecturerFactory implements UserFactory{
    @Override
    public User createUser(String name, String surname) {
        return new Lecturer(name,surname);
    }
}
