package com.xpanse.ims.service;

import com.xpanse.ims.constants.ErrorConstants;
import com.xpanse.ims.model.TokenRequest;
import com.xpanse.ims.model.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

/**
 * Token Service.
 */
@Component
public class TokenService {

    private final String clientId;

    private final String clientSecret;

    private final String authURL;

    private final RestApiService restApiService;

    private volatile String token;
    private volatile long tokenExpiryTime;
    private volatile long expiresIn;

    /**
     * Constructs a new TokenService.
     *
     * @param clientId       cognito user pool client id
     * @param clientSecret   cognito user pool client secret
     * @param authURL        cognito authentication url
     * @param restApiService autowiring RestApiService
     */
    public TokenService(@Value("${cognito.client.id}") final String clientId,
                        @Value("${cognito.client.secret}") final String clientSecret,
                        @Value("${cognito.token.url}") final String authURL,
                        final RestApiService restApiService) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authURL = authURL;
        this.restApiService = restApiService;
    }

    /**
     * get token.
     *
     * @return token
     */
    public String getToken() {
        if (token == null || isTokenExpired()) {
            synchronized (this) {
                if (token == null || isTokenExpired()) {
                    fetchToken();
                }
            }
        }
        return token;
    }

    /**
     * invokes api to fetch token.
     */
    private void fetchToken() {
        TokenRequest request = TokenRequest.builder().clientId(clientId).clientSecret(clientSecret).build();
        ResponseEntity<TokenResponse> response = restApiService.postApiDataWithHeaders(authURL, request, createHeaders(), TokenResponse.class);
        HttpStatusCode statusCode = response.getStatusCode();

        TokenResponse tokenResponse = response.getBody();

        if (statusCode.is2xxSuccessful() && null != tokenResponse
                && null != tokenResponse.getResponseData() && null != tokenResponse.getResponseData().getAuthenticationResult()) {
            this.token = tokenResponse.getResponseData().getAuthenticationResult().getIdToken();
            this.expiresIn = Math.round(tokenResponse.getResponseData().getAuthenticationResult().getExpiresIn());
            this.tokenExpiryTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(expiresIn);
        } else {
            throw new IllegalStateException(ErrorConstants.TOKEN_API_FAILURE_MSG);
        }

    }

    /**
     * checks token expiration.
     *
     * @return boolean value
     */
    private boolean isTokenExpired() {
        return System.currentTimeMillis() >= tokenExpiryTime;
    }


    /**
     * create http headers.
     *
     * @return HttpHeaders
     */
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }
}
