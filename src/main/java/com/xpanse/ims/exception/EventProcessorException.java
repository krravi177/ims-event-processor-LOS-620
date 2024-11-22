package com.xpanse.ims.exception;

import lombok.Getter;

/**
 * Custom exception class for event processor.
 */
@Getter
public class EventProcessorException extends Exception {

    private final String message;

    /**
     * Constructs a new EventProcessorException with message.
     *
     * @param message custom message
     */
    public EventProcessorException(final String message) {
        super(message);
        this.message = message;
    }

    /**
     * Constructs a new EventProcessorException with message and cause.
     *
     * @param message custom message
     * @param cause   {@Link Throwable}
     */
    public EventProcessorException(final String message, final Throwable cause) {
        super(message, cause);
        this.message = message;
    }

}
