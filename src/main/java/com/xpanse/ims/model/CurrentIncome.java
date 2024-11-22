package com.xpanse.ims.model;

import lombok.Data;
import java.util.List;

@Data
public class CurrentIncome {
    private List<CurrentIncomeItemsItem> currentIncomeItems;
}