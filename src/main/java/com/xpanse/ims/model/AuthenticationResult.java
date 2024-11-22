package com.xpanse.ims.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthenticationResult {
    @JsonProperty("AccessToken")
    private String accessToken;
    @JsonProperty("ExpiresIn")
    private Double expiresIn;
    @JsonProperty("IdToken")
    private String idToken;
    @JsonProperty("RefreshToken")
    private String refreshToken;
    @JsonProperty("TokenType")
    private String tokenType;
}
