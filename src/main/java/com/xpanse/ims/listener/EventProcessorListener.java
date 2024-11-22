package com.xpanse.ims.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xpanse.ims.constants.ErrorConstants;
import com.xpanse.ims.exception.EventProcessorException;
import com.xpanse.ims.model.CLSEvent;
import com.xpanse.ims.model.CLSSQSEvent;
import com.xpanse.ims.model.IncomeRequest;
import com.xpanse.ims.model.IncomeRequestAck;
import com.xpanse.ims.service.AuditService;
import com.xpanse.ims.service.IncomeRequestService;
import com.xpanse.ims.service.TokenService;
import com.xpanse.ims.util.DataMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class EventProcessorListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventProcessorListener.class);

    private static final String LOG_CLASS_REF = "ims-event-processor | EventProcessorListener - ";

    private final DataMapper dataMapper;

    private final TokenService tokenService;

    private final IncomeRequestService incomeRequestService;

    private final AuditService auditService;

    private final ObjectMapper objectMapper;

    /**
     * Constructs a new EventProcessorListener which prepares income request
     * get new Token and invokes income request api
     *
     * @param dataMapper           prepares income request
     * @param tokenService         handles token
     * @param incomeRequestService invoke income request api
     * @param auditService         pushes messages to audit queue
     * @param objectMapper         serialize Java objects into JSON string
     */
    public EventProcessorListener(final DataMapper dataMapper, final TokenService tokenService,
                                  final IncomeRequestService incomeRequestService,
                                  final AuditService auditService, final ObjectMapper objectMapper) {
        this.dataMapper = dataMapper;
        this.tokenService = tokenService;
        this.incomeRequestService = incomeRequestService;
        this.auditService = auditService;
        this.objectMapper = objectMapper;
    }

    /**
     * Processes the incoming json event payload from the queue.
     *
     * @param message incoming payload
     * @throws EventProcessorException custom exception
     */
    @SqsListener("${aws.sqs.queue-name}")
    public void handleMessage(final CLSSQSEvent message) throws EventProcessorException {
        try {
            LOGGER.info("{} handleMessage - message received from event bus: {}", LOG_CLASS_REF, message);
            final String eventId = message.getDetail().getAttributes().getLoanEventId();
            final String transactionCode = message.getDetail().getAttributes().getSubmitterTransactionCode();
            final String eventMessage = getJsonString(message);
            this.auditService.sendAuditMessage("message received from event bus", eventMessage, eventId, transactionCode);

            CLSEvent event = message.getDetail();
            IncomeRequest request = dataMapper.createIncomeRequest(event, eventMessage, eventId, transactionCode);

            LOGGER.info("{} handleMessage - getting token - eventId: {}, submitterTransactionCode: {}",
                    LOG_CLASS_REF, eventId, transactionCode);
            String token = tokenService.getToken();
            if (ObjectUtils.isEmpty(token)) {
                throw new EventProcessorException(ErrorConstants.TOKEN_EMPTY_MSG);
            }

            this.auditService.sendAuditMessage("Income Request API called", getJsonString(request), eventId, transactionCode);
            LOGGER.info("{} handleMessage - income request api called - eventId: {} - submitterTransactionCode: {} ",
                    LOG_CLASS_REF, eventId, transactionCode);

            IncomeRequestAck incomeRequestAck = incomeRequestService.sendIncomeRequest(request, token);
            if (null != incomeRequestAck) {
                this.auditService.sendAuditMessage("Income Request API response", getJsonString(incomeRequestAck), eventId, transactionCode);

                LOGGER.info("{} handleMessage - income request acknowledgment {} - eventId: {} - submitterTransactionCode: {}",
                        LOG_CLASS_REF, incomeRequestAck, eventId, transactionCode);
            } else {
                throw new EventProcessorException(ErrorConstants.RESPONSE_MSG);
            }

        } catch (final Exception e) {
            LOGGER.error("{} handleMessage - failed to process event message: {}",
                    LOG_CLASS_REF, e.getMessage(), e);
        }
    }

    private <T> String getJsonString(final T t) throws EventProcessorException {
        try {
            return objectMapper.writeValueAsString(t);
        } catch (final JsonProcessingException e) {
            throw new EventProcessorException(ErrorConstants.JSON_PARSER_FAILURE_MSG, e);
        }
    }

}
