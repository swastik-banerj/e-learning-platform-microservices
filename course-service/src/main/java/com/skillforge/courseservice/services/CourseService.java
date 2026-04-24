package com.skillforge.courseservice.services;

import com.skillforge.courseservice.dto.RequestCourse;
import com.skillforge.courseservice.dto.ResponseCourse;
import com.skillforge.courseservice.dto.ValidationResponse;
import com.skillforge.courseservice.models.Course;
import com.skillforge.courseservice.repositories.CourseRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Data
public class CourseService {

    private final UserValidationService userValidationService;
    private final CourseRepository courseRepository;

    public ResponseEntity<?> createCourse(String token, RequestCourse requestCourse) {
        ValidationResponse user = userValidationService.validateUser(token);

        if(!user.getRole().equals("ROLE_INSTRUCTOR")){
            throw new RuntimeException("Only instructors can create courses");
        }

        Course course = new Course();
        course.setInstructorId(Long.valueOf(user.getUserId()));
        course.setTitle(requestCourse.getTitle());
        course.setDescription(requestCourse.getDescription());

        Course savedCourse = courseRepository.save(course);

        return ResponseEntity.ok(convertToDto(savedCourse));

    }

    public ResponseEntity<?> getAllCourses(String instructorId) {
        List<Course> courses = courseRepository.findByInstructorId(Long.valueOf(instructorId));

        return ResponseEntity.ok(courses.stream()
                .map(course -> convertToDto(course))
                .toList());

    }

    public ResponseCourse getCourseById(String courseId) {
        Course course = courseRepository.findById(Long.valueOf(courseId))
                .orElseThrow(() -> new RuntimeException("Course not found"));

        return convertToDto(course);
    }

    public ResponseEntity<?> updateCourse(Long courseId, String jwtToken, RequestCourse requestCourse) {

        ValidationResponse user = userValidationService.validateUser(jwtToken);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if(!course.getInstructorId().equals(Long.valueOf(user.getUserId()))){
            throw new RuntimeException("Unauthorized");
        }

        course.setTitle(requestCourse.getTitle());
        course.setDescription(requestCourse.getDescription());

        Course updatedCourse = courseRepository.save(course);
        return ResponseEntity.ok(convertToDto(updatedCourse));

    }

    public ResponseEntity<?> deleteCourse(Long courseId, String jwtToken) {

        ValidationResponse user = userValidationService.validateUser(jwtToken);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if(!course.getInstructorId().equals(Long.valueOf(user.getUserId()))){
            throw new RuntimeException("Unauthorized");
        }

        courseRepository.delete(course);

        return ResponseEntity.ok("Course deleted successfully");
    }

    public ResponseCourse convertToDto(Course savedCourse) {
        ResponseCourse responseCourse = new ResponseCourse();
        responseCourse.setInstructorId(savedCourse.getInstructorId());
        responseCourse.setTitle(savedCourse.getTitle());
        responseCourse.setDescription(savedCourse.getDescription());
        responseCourse.setCreatedAt(savedCourse.getCreatedAt());
        responseCourse.setUpdatedAt(savedCourse.getUpdatedAt());
        return responseCourse;
    }

}
