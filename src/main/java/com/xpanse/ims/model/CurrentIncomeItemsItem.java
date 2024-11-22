package com.xpanse.ims.model;

import lombok.Data;

@Data
public class CurrentIncomeItemsItem {
    private int sequenceNumber;
    private Object currentIncomeMonthlyTotalAmount;
    private String incomeType;
    private boolean employmentIncomeIndicator;
    private Links links;
    private String id;
}