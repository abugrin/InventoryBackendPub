package com.vinteo.inventory.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
public class JwtResponse {

    private final String type = "Bearer";

    private String accessToken;
    private String refreshToken;

    @Setter
    private String message;

    public JwtResponse(){

    }

    public JwtResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}