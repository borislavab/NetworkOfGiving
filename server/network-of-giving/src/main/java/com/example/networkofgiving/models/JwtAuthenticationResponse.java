package com.example.networkofgiving.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {
    @JsonProperty("access_token")
    private final String accessToken;

    @JsonProperty("token_type")
    private final String tokenType = "Bearer";

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }
}
