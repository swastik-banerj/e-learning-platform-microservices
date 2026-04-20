package com.skillforge.enrollmentservice.controllers;

import com.skillforge.enrollmentservice.services.EnrollmentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@AllArgsConstructor
@RequestMapping("/api/enrollment")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping("/enroll/{courseId}")
    public ResponseEntity<?> enrollUser(@PathVariable String courseId, @RequestHeader("Authorization") String jwtToken){
        String token = jwtToken.substring(7);
        return ResponseEntity.ok(enrollmentService.enrollUser(courseId,token));
    }

    @GetMapping("/my-courses")
    public ResponseEntity<?> getUserCourses(@RequestHeader("Authorization") String jwtToken){
        String token = jwtToken.substring(7);
        return ResponseEntity.ok(enrollmentService.getUserCourses(token));
    }

    @GetMapping("/enroll/check/{courseId}")
    public Boolean checkUserAndCourse(@PathVariable Long courseId, @RequestHeader("Authorization") String jwtToken){
        String token = jwtToken.substring(7);
        return ResponseEntity.ok(enrollmentService.checkUserAndCourse(token, courseId)).hasBody();
    }
}
