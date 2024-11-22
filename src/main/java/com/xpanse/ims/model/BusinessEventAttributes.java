package com.xpanse.ims.model;

import com.xpanse.ims.constants.ErrorConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BusinessEventAttributes {
    @NotBlank(message = ErrorConstants.DATA_SCHEMA_REQ_MSG)
    private String dataSchema;
    @NotBlank(message = ErrorConstants.DATA_CONTENT_TYPE_REQ_MSG)
    private String dataContentType;
}