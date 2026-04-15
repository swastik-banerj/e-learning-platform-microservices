package com.skillforge.courseservice.controllers;

import com.skillforge.courseservice.dto.RequestCourse;
import com.skillforge.courseservice.services.CourseService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/api/course/instructor")
public class CourseController {

    private CourseService courseService;

    @PostMapping("/create-course")
    public ResponseEntity<?> createCourse(@RequestHeader("Authorization") String token, @RequestBody RequestCourse requestCourse){
        return ResponseEntity.ok(courseService.createCourse(token,requestCourse));
    }
}
