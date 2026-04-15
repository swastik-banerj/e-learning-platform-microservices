package com.skillforge.courseservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestCourse {
    private String title;
    private String description;
}
