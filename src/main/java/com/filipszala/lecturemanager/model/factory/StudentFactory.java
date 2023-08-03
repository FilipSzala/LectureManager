package com.filipszala.lecturemanager.model.factory;

import com.filipszala.lecturemanager.model.Student;
import com.filipszala.lecturemanager.model.User;

public class StudentFactory implements UserFactory {
    @Override
    public User createUser(String name, String surname) {
        return new Student(name,surname);
    }
}
