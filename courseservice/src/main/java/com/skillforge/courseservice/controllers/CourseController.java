package com.skillforge.courseservice.controllers;

import com.skillforge.courseservice.dto.RequestCourse;
import com.skillforge.courseservice.services.CourseService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/api/course/instructor")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/create-course")
    public ResponseEntity<?> createCourse(@RequestHeader("Authorization") String jwtToken, @RequestBody RequestCourse requestCourse){
        String token = jwtToken.substring(7);
        return ResponseEntity.ok(courseService.createCourse(token,requestCourse));
    }

    @GetMapping("/{instructorId}/allcourses")
    public ResponseEntity<?> getAllCourses(@PathVariable String instructorId){
        return ResponseEntity.ok(courseService.getAllCourses(instructorId));
    }

    @GetMapping("/{courseId}/getcourse")
    public ResponseEntity<?> getCourse(@PathVariable String courseId){
        return ResponseEntity.ok(courseService.getCourseById(courseId));
    }

    @PutMapping("/{courseId}/updatecourse")
    public ResponseEntity<?> updateCourse(@PathVariable Long courseId,
                                          @RequestHeader("Authorization") String jwtToken, @RequestBody RequestCourse requestCourse){
        return ResponseEntity.ok(courseService.updateCourse(courseId, jwtToken, requestCourse));
    }

    @DeleteMapping("/{courseId}/deletecourse")
    public ResponseEntity<?> deleteCourse(@PathVariable Long courseId,
                                          @RequestHeader("Authorization") String jwtToken){
        return ResponseEntity.ok(courseService.deleteCourse(courseId, jwtToken));
    }

}
