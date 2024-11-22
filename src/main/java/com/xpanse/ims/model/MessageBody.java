package com.xpanse.ims.model;

import com.xpanse.ims.constants.ErrorConstants;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageBody {
    @NotNull(message = ErrorConstants.DEAL_REQ_MSG)
    private Deal deal;
}