package com.skillforge.enrollmentservice.dto;

import lombok.Data;

@Data
public class EnrollmentResponse {
    private Long courseId;
    private String title;
    private String description;
}
