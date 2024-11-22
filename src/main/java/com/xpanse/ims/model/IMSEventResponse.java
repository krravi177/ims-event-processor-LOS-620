package com.xpanse.ims.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class IMSEventResponse {
    private String specVersion;
    private String submitterTransactionCode;
    private String loanEventId;
    private String requestStatusCode;
    private String requestStatusCodeDescription;
}
