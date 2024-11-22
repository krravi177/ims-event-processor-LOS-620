package com.xpanse.ims.model;

import lombok.Data;

@Data
public class TokenResponse {

    private String responseCode;
    private String responseMessage;
    private ResponseData responseData;
}
