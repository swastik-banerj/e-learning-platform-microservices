package com.skillforge.enrollmentservice.repositories;

import com.skillforge.enrollmentservice.models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);

    List<Enrollment> findByUserId(Long userId);
}
