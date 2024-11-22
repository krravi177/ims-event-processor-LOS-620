package com.xpanse.ims.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LegalEntity {
    private String globalLegalEntityIdentifier;
    private String fullName;
}