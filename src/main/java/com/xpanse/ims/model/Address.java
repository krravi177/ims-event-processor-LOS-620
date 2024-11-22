package com.xpanse.ims.model;

import lombok.Data;

@Data
public class Address {
    private String cityName;
    private String addressAdditionalLineText;
    private String countryCode;
    private String addressLineText;
    private String postalCode;
    private String stateCode;
    private String countyName;
    private String countyCode;
    private String countryName;
    private String stateName;
}