package com.xpanse.ims.model;

import lombok.Data;

@Data
public class EmployersItem {
    private int sequenceNumber;
    private Address address;
    private String id;
    private Employment employment;
    private LegalEntity legalEntity;
}