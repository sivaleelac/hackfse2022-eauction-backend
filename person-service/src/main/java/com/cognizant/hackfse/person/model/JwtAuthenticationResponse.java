package com.cognizant.hackfse.person.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtAuthenticationResponse {

    private String accessToken;
    private String emailAddress;
    private String classification;
    private final  String tokenType = "Bearer";

}
