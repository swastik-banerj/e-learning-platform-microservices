package com.skillforge.enrollmentservice.services;

import com.skillforge.enrollmentservice.dto.ResponseCourse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Data
public class CourseValidationService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public ResponseCourse getCourse(String courseId){

        ResponseCourse response = webClientBuilder.build()
                .get()
                .uri("http://COURSESERVICE/api/course/instructor/"+ courseId+"/getcourse")
                .retrieve()
                .bodyToMono(ResponseCourse.class)
                .block();

        //System.out.println("RAW RESPONSE: " + response);
        return response;
    }
}
