package com.xpanse.ims.model;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class RolesItem {
    private String partyRoleType;
    private Borrower borrower;
    private List<PartyRoleIdentifierItem> partyRoleIdentifiers;
    private String id;
}