package com.skillforge.courseservice.services;

import com.skillforge.courseservice.dto.RequestCourse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    public ResponseEntity<?> createCourse(String token, RequestCourse requestCourse) {
        return ResponseEntity.ok("Done");
    }
}
