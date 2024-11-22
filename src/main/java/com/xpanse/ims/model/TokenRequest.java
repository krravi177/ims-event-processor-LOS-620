package com.xpanse.ims.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenRequest {
    private String clientId;
    private String clientSecret;
}
