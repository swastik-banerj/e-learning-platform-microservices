package com.skillforge.enrollmentservice.services;

import com.skillforge.enrollmentservice.dto.ValidationResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
@Data
public class UserValidationService {

    @Autowired
    private WebClient userServiceWebClient;

    public ValidationResponse validateUser(String token){
        try{

            return userServiceWebClient.get()
                    .uri("http://AUTH-SERVICE/api/auth/validate")
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(ValidationResponse.class)
                    .block();

        } catch (WebClientResponseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
