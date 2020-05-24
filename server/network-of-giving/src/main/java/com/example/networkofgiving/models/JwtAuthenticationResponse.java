package com.example.networkofgiving.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {
    @JsonProperty("access_token")
    private final String accessToken;

    @JsonProperty("expires_in")
    private final Integer expiresIn;

    @JsonProperty("token_type")
    private final String tokenType = "Bearer";

    public JwtAuthenticationResponse(String accessToken, Integer expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }
}
