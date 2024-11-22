package com.xpanse.ims.constants;

import com.xpanse.ims.model.LegalEntity;
import com.xpanse.ims.model.PartiesItem;
import com.xpanse.ims.model.RolesItem;
import java.util.Collections;

/**
 * This class is for Income Request api request body constants.
 */
public class IncomeRequestConstants {

    public static final String EVENT_TYPE = "PlaceOrder";

    public static final String EVENT_CODE = "30";

    public static final String PARTY_ROLE_TYPE = "Borrower";

    public static final String AUTH_HEADER_PREFIX = "Bearer ";

    public static final String PARTIES_VALIDATION_MSG = "Event detail.messageBody.deal.parties does not have parties which matches with given entityID";

    public static final PartiesItem REQUESTING_PARTY = createParty("party3", 2, "XPANSQA", "RequestingParty");

    public static final PartiesItem SUBMITTING_PARTY = createParty("party4", 3, "Freedom Mortgage", "SubmittingParty");

    /**
     * Create Party based on given values.
     *
     * @param id             party id
     * @param sequenceNumber sequence number
     * @param fullName       full name
     * @param partyRoleType  party role type
     * @return {@Link PartiesItem}
     */
    private static PartiesItem createParty(final String id, final int sequenceNumber, final String fullName,
                                           final String partyRoleType) {
        return PartiesItem.builder()
                .id(id)
                .sequenceNumber(sequenceNumber)
                .legalEntity(LegalEntity.builder().fullName(fullName).build())
                .roles(Collections.singletonList(RolesItem.builder().partyRoleType(partyRoleType).build()))
                .build();
    }
}
