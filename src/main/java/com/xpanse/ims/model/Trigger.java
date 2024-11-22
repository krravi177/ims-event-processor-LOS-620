package com.xpanse.ims.model;

import lombok.Data;

@Data
public class Trigger {
    private String trackingItemStatus;
    private String trackingItemStatusDesc;
    private String trackingItemDesc;
    private String borroweSequenceNumber;
    private String trackingItemID;
    private int currentLoanStatus;
    private String entityID;
    private String currentLoanStatusDesc;
}