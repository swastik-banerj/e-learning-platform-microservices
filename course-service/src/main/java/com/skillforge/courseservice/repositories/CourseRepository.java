package com.skillforge.courseservice.repositories;

import com.skillforge.courseservice.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    Course findByInstructorId(Long instructorId);
}
