package com.xpanse.ims.service;

import com.xpanse.ims.constants.ErrorConstants;
import com.xpanse.ims.constants.IncomeRequestConstants;
import com.xpanse.ims.exception.EventProcessorException;
import com.xpanse.ims.model.IncomeRequest;
import com.xpanse.ims.model.IncomeRequestAck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Service class invokes income request apis.
 */
@Component
public class IncomeRequestService {

    private final String incomeRequestURL;

    private final RestApiService restApiService;

    /**
     * Constructs a new IncomeRequestService.
     *
     * @param incomeRequestURL income request api URL
     * @param restApiService   {@Link RestApiService} service which invokes api using RestTemplate
     */
    public IncomeRequestService(@Value("${income.request.url}") final String incomeRequestURL,
                                final RestApiService restApiService) {
        this.incomeRequestURL = incomeRequestURL;
        this.restApiService = restApiService;
    }

    /**
     * Invokes income request api.
     *
     * @param incomeRequest income request api request body
     */
    public IncomeRequestAck sendIncomeRequest(final IncomeRequest incomeRequest, final String token) throws EventProcessorException {
        ResponseEntity<IncomeRequestAck> response = restApiService.postApiDataWithHeaders(incomeRequestURL, incomeRequest, createHeaders(token), IncomeRequestAck.class);

        if (null != response) {
            return response.getBody();
        } else {
            throw new EventProcessorException(ErrorConstants.RESPONSE_MSG);
        }
    }

    /**
     * create http headers.
     *
     * @return HttpHeaders
     */
    private HttpHeaders createHeaders(final String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.AUTHORIZATION, IncomeRequestConstants.AUTH_HEADER_PREFIX + token);
        return headers;
    }
}
