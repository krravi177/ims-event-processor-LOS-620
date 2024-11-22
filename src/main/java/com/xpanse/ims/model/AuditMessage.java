package com.xpanse.ims.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditMessage {

    private String applicationName;
    private String action;
    private String actionPayload;
    private String auditDate;

}
