package com.xpanse.ims.model;

import com.xpanse.ims.constants.ErrorConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Attributes {
    @NotBlank(message = ErrorConstants.SPEC_VERSION_REQ_MSG)
    private String specVersion;
    @NotBlank(message = ErrorConstants.SOURCE_USR_NAME_REQ_MSG)
    private String sourceUserName;
    @NotBlank(message = ErrorConstants.SOURCE_TIME_REQ_MSG)
    private String sourceTime;
    @NotBlank(message = ErrorConstants.EVT_TYPE_REQ_MSG)
    private String eventType;
    @NotBlank(message = ErrorConstants.SOURCE_REQ_MSG)
    private String source;
    @NotBlank(message = ErrorConstants.SUBMITTER_TRANS_CODE_REQ_MSG)
    private String submitterTransactionCode;
    private String loanEventId;
}