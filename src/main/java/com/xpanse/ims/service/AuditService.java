package com.xpanse.ims.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xpanse.ims.constants.ErrorConstants;
import com.xpanse.ims.exception.EventProcessorException;
import com.xpanse.ims.model.AuditMessage;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Service which calls audit api which puts the message in audit queue.
 */
@Service
public class AuditService {

    private static final Logger LOGGER = LogManager.getLogger(AuditService.class);

    private static final String LOG_CLASS_REF = "ims-event-processor | AuditService - ";

    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");

    private final String applicationName;
    private final SqsTemplate sqsTemplate;
    private final String auditQueue;
    private final ObjectMapper objectMapper;


    /**
     * Constructs an AuditService with dependencies for message serialization and SQS messaging.
     *
     * @param sqsTemplate     the service for sending messages to SQS
     * @param auditQueue      the SQS audit queue URL
     * @param applicationName the name of the application to include in audit logs
     * @param objectMapper    serialize Java objects into JSON string
     */
    public AuditService(final SqsTemplate sqsTemplate,
                        @Value("${aws.sqs.audit.queue}") final String auditQueue,
                        @Value("${spring.application.name}") final String applicationName,
                        final ObjectMapper objectMapper) {
        this.sqsTemplate = sqsTemplate;
        this.auditQueue = auditQueue;
        this.applicationName = applicationName;
        this.objectMapper = objectMapper;
    }

    /**
     * Audits an action by creating and sending an audit message to the audit SQS queue.
     * The message contains details about the action, including a timestamp, and is serialized to JSON format.
     *
     * @param action  the action to be audited
     * @param payload a description or additional details related to the action
     * @param eventId event id
     * @param transactionCode submitter transactionCode
     * @throws EventProcessorException if there is an error serializing the message or sending it to SQS
     */
    public void sendAuditMessage(final String action, final String payload, final String eventId,
                                 final String transactionCode) throws EventProcessorException {
        try {

            String timestamp = LocalDateTime.now().format(DATETIME_FORMAT);

            AuditMessage auditMessage = AuditMessage.builder().actionPayload(payload).action(action)
                    .applicationName(this.applicationName).auditDate(timestamp)
                    .build();

            sqsTemplate.send(auditQueue, objectMapper.writeValueAsString(auditMessage));
        } catch (final JsonProcessingException e) {
            LOGGER.error("{} - sendAuditMessage - failed to parse audit message - eventId: {}, submitterTransactionCode: {}, Action={}",
                    LOG_CLASS_REF, eventId, transactionCode, action);
            throw new EventProcessorException(ErrorConstants.JSON_PARSER_FAILURE_MSG, e);
        } catch (final Exception e) {
            LOGGER.error("{} - sendAuditMessage - failed to push audit message - eventId: {}, submitterTransactionCode: {}, Action={}",
                    LOG_CLASS_REF, eventId, transactionCode, action);
            throw new EventProcessorException(ErrorConstants.AUDIT_SERVICE_FAILURE_MSG, e);
        }
    }
}
