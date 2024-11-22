package com.xpanse.ims.model;

import lombok.Data;
import java.util.List;

@Data
public class Deal {
    private List<LoansItem> loans;
    private List<PartiesItem> parties;
}