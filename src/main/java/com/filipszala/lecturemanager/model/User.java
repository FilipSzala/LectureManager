package com.filipszala.lecturemanager.model;

import lombok.Getter;
import lombok.Setter;


public interface User {
    String name = null;
    String surname = null;
    void displayUser();
    User deleteUser();
    User updateUser();
}
