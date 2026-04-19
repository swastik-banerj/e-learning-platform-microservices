package com.skillforge.enrollmentservice.services;

import com.skillforge.enrollmentservice.dto.EnrollmentResponse;
import com.skillforge.enrollmentservice.dto.ResponseCourse;
import com.skillforge.enrollmentservice.dto.ValidationResponse;
import com.skillforge.enrollmentservice.models.Enrollment;
import com.skillforge.enrollmentservice.repositories.EnrollmentRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Data
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final UserValidationService userValidationService;
    private final CourseValidationService courseValidationService;

    public ResponseEntity<Enrollment> enrollUser(String courseId, String token) {
        // validate user
        ValidationResponse user = userValidationService.validateUser(token);

        // check role
        if(!user.getRole().equals("ROLE_STUDENT")){
            throw new RuntimeException("Only students can enroll");
        }

        // check course exists
        ResponseCourse course = courseValidationService.getCourse(courseId);

        if(course == null){
            throw new RuntimeException("Course not found");
        }

        // preventing duplicate
        boolean exists = enrollmentRepository.existsByUserIdAndCourseId(Long.valueOf(user.getUserId()), Long.valueOf(courseId));

        if(exists){
            throw new RuntimeException("Already enrolled");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setUserId(Long.valueOf(user.getUserId()));
        enrollment.setCourseId(Long.valueOf(courseId));

        enrollmentRepository.save(enrollment);

        return ResponseEntity.ok(enrollment);
    }
}
