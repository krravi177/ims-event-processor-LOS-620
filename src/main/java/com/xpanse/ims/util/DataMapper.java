package com.xpanse.ims.util;

import com.xpanse.ims.constants.IncomeRequestConstants;
import com.xpanse.ims.exception.EventProcessorException;
import com.xpanse.ims.model.CLSEvent;
import com.xpanse.ims.model.Deal;
import com.xpanse.ims.model.EventRequestDetails;
import com.xpanse.ims.model.IncomeRequest;
import com.xpanse.ims.model.PartiesItem;
import com.xpanse.ims.model.RolesItem;
import com.xpanse.ims.service.AuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * DataMapper - creates Income Request api request body
 * based on condition - filter parties and roles by "Borrower" partyRoleType
 * and partyRoleIdentifiers with given entity ID.
 */
@Component
public class DataMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataMapper.class);

    private static final String LOG_CLASS_REF = "ims-event-processor | DataMapper - ";

    private final AuditService auditService;

    /**
     * Constructs DataMapper.
     * @param auditService pushes messages to audit queue
     */
    public DataMapper(final AuditService auditService) {
        this.auditService = auditService;
    }

    /**
     * Constructs income request api request body.
     *
     * @param clsEvent sqs payload
     * @param eventMessage event message json string
     * @param eventId event id
     * @param transactionCode submitter transactionCode
     * @return IncomeRequest
     * @throws EventProcessorException custom exception
     */
    public IncomeRequest createIncomeRequest(final CLSEvent clsEvent, final String eventMessage,
                                             final String eventId, final String transactionCode) throws EventProcessorException {
        IncomeRequest incomeRequest = new IncomeRequest();

        EventRequestDetails eventRequestDetails = new EventRequestDetails();
        eventRequestDetails.setEventType(IncomeRequestConstants.EVENT_TYPE);
        eventRequestDetails.setEventCode(IncomeRequestConstants.EVENT_CODE);
        eventRequestDetails.setLoanEventId(clsEvent.getAttributes().getLoanEventId());
        eventRequestDetails.setTransactionCode(clsEvent.getAttributes().getSubmitterTransactionCode());
        eventRequestDetails.setAttachment(clsEvent.getAttachment());

        Deal deal = clsEvent.getMessageBody().getDeal();
        List<PartiesItem> parties = getParty(clsEvent.getBusinessEventData().getTrigger().getEntityID(), deal.getParties());
        if (!parties.isEmpty()) {
            //adding requesting party
            parties.add(IncomeRequestConstants.REQUESTING_PARTY);

            //adding submitting party
            parties.add(IncomeRequestConstants.SUBMITTING_PARTY);
        } else {
            LOGGER.error("{} createIncomeRequest - empty parties - eventId: {}, submitterTransactionCode: {}",
                    LOG_CLASS_REF, eventId, transactionCode);
            this.auditService.sendAuditMessage(IncomeRequestConstants.PARTIES_VALIDATION_MSG,
                    eventMessage, eventId, transactionCode);
            throw new EventProcessorException(IncomeRequestConstants.PARTIES_VALIDATION_MSG);
        }

        deal.setParties(parties);
        clsEvent.getMessageBody().setDeal(deal);
        eventRequestDetails.setMessageBody(clsEvent.getMessageBody());

        incomeRequest.setEventRequestDetails(eventRequestDetails);
        return incomeRequest;
    }

    /**
     * filters event parties based entityId, and partyRoleType.
     *
     * @param entityId entity id from queue payload
     * @param parties  PartiesItem List
     * @return PartiesItem List
     */
    private List<PartiesItem> getParty(final String entityId, final List<PartiesItem> parties) {
        return parties.stream()
                .filter(party -> !this.filterRoles(entityId, party).isEmpty())
                .map(party -> {
                    PartiesItem partiesItem = PartiesItem.builder().build();
                    BeanUtils.copyProperties(party, partiesItem);
                    partiesItem.setRoles(this.filterRoles(entityId, party));
                    return partiesItem;
                }).collect(Collectors.toList());
    }

    /**
     * filter Roles based entityId, and partyRoleType.
     *
     * @param entityId String
     * @param party    PartiesItem
     * @return RolesItem List
     */
    private List<RolesItem> filterRoles(final String entityId, final PartiesItem party) {
        return party.getRoles()
                .stream()
                .filter(rolesItem ->
                        IncomeRequestConstants.PARTY_ROLE_TYPE.equals(rolesItem.getPartyRoleType())
                                && Objects.nonNull(rolesItem.getPartyRoleIdentifiers())
                                && rolesItem.getPartyRoleIdentifiers()
                                .stream()
                                .anyMatch(partyRoleIdentifierItem -> entityId.equals(partyRoleIdentifierItem.getPartyRoleIdentifier())))
                .toList();
    }


}