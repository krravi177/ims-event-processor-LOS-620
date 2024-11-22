package com.xpanse.ims.model;

import lombok.Data;

@Data
public class Employment {
    private String employmentStartDate;
    private boolean employmentBorrowerSelfEmployedIndicator;
    private Object employmentMonthlyIncomeAmount;
    private String employmentEndDate;
    private int employmentMonthsOnJobCount;
    private String employmentStatusType;
    private String employmentClassificationType;
    private String employmentReportedDate;
}