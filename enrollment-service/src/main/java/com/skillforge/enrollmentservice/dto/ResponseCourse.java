package com.skillforge.enrollmentservice.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseCourse {
    private String title;
    private String description;
    private Long instructorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
