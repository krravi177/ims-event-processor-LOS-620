package com.xpanse.ims.model;

import lombok.Data;
@Data
public class EventAckDetails {
    private String eventType;
    private String transactionCode;
    private String imsId;
}
