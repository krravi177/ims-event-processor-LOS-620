package com.xpanse.ims.model;

import lombok.Data;
import java.util.List;

@Data
public class LoansItem {
    private List<LoanIdentifiersItem> loanIdentifiers;
}