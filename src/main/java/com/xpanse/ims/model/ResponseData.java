package com.xpanse.ims.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseData {
    @JsonProperty("AuthenticationResult")
    private AuthenticationResult authenticationResult;
    @JsonProperty("ChallengeParameters")
    private ChallengeParameters challengeParameters;

}
