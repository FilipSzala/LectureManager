package com.filipszala.lecturemanager.model.factory;

import com.filipszala.lecturemanager.model.Professor;
import com.filipszala.lecturemanager.model.User;

public class ProfessorFactory implements UserFactory {
    @Override
    public User createUser(String name, String surname) {
        return new Professor(name,surname);
    }
}
