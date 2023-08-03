package com.filipszala.lecturemanager.model.factory;

import com.filipszala.lecturemanager.model.User;

public interface UserFactory {
    public User createUser(String name, String surname);
}
