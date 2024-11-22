package com.xpanse.ims.model;

import com.xpanse.ims.constants.ErrorConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BusinessEventData {
    @NotBlank(message = ErrorConstants.DIVISION_REQ_MSG)
    private String division;
    @NotBlank(message = ErrorConstants.CHANNEL_REQ_MSG)
    private String channel;
    @NotNull(message = ErrorConstants.TRIGGER_REQ_MSG)
    private Trigger trigger;
    @NotBlank(message = ErrorConstants.LOAN_NUM_REQ_MSG)
    private String loanNumber;
}