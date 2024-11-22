package com.xpanse.ims.model;

import lombok.Data;
import java.util.List;

@Data
public class Borrower {
    private List<EmployersItem> employers;
    private CurrentIncome currentIncome;
    private List<ResidencesItem> residences;
    private String borrowerBirthDate;
}