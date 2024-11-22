package com.xpanse.ims.model;

import com.xpanse.ims.constants.ErrorConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class CLSEvent {
    @NotNull(message = ErrorConstants.BUSINESS_ATTR_REQ_MSG)
    @Valid
    private BusinessEventAttributes businessEventAttributes;
    @NotNull(message = ErrorConstants.MSG_BODY_REQ_MSG)
    private MessageBody messageBody;
    private List<AttachmentItem> attachment;
    @NotNull(message = ErrorConstants.BUSINESS_EVT_DATA_REQ_MSG)
    @Valid
    private BusinessEventData businessEventData;
    @NotNull(message = ErrorConstants.ATTR_REQ_MSG)
    @Valid
    private Attributes attributes;
}