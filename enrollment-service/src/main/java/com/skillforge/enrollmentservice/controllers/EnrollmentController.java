package com.skillforge.enrollmentservice.controllers;

import com.skillforge.enrollmentservice.services.EnrollmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/enrollment")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping("/enroll/{courseId}")
    public ResponseEntity<?> enrollUser(@PathVariable String courseId, @RequestHeader("Authorization") String jwtToken){
        return ResponseEntity.ok(enrollmentService.enrollUser(courseId,jwtToken));
    }

    @GetMapping("/my-courses")
    public ResponseEntity<?> getUserCourses(@RequestHeader("Authorization") String jwtToken){
        return ResponseEntity.ok(enrollmentService.getUserCourses(jwtToken));
    }

    @GetMapping("/check/{courseId}")
    public ResponseEntity<?> checkUserAndCourse(@PathVariable Long courseId,
                                                @RequestHeader("Authorization") String jwtToken){
        return ResponseEntity.ok(Map.of("enrolled", enrollmentService.checkUserAndCourse(jwtToken, courseId)));
    }
}
