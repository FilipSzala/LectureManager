package com.filipszala.lecturemanager.model;

import lombok.Getter;
import lombok.Setter;


public interface User {
    void displayUser();
    User deleteUser();
    User updateUser();
}
