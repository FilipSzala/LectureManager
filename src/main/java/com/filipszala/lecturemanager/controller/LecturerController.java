package com.filipszala.lecturemanager.controller;

import com.filipszala.lecturemanager.model.Lecturer;
import com.filipszala.lecturemanager.model.User;
import com.filipszala.lecturemanager.repository.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class LecturerController {
    private LecturerRepository lecturerRepository;
    @Autowired
    public LecturerController(LecturerRepository lecturerRepository) {
        this.lecturerRepository = lecturerRepository;
    }

    @PostMapping("/createLecturer")
    @ResponseBody
    public User createLecturer(@RequestBody Lecturer user){

        return lecturerRepository.save(user);
    }
}
