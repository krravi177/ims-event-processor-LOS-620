package com.xpanse.ims.model;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class PartiesItem {
    private int sequenceNumber;
    private Individual individual;
    private List<RolesItem> roles;
    private List<TaxpayerIdentifiersItem> taxpayerIdentifiers;
    private String id;
    private LegalEntity legalEntity;
}