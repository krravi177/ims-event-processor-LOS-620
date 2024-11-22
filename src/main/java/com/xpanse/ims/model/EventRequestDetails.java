package com.xpanse.ims.model;

import lombok.Data;
import java.util.List;

@Data
public class EventRequestDetails {
    private String eventType;
    private String eventCode;
    private String transactionCode;
    private String loanEventId;
    private MessageBody messageBody;
    private List<AttachmentItem> attachment;
}
