package com.xpanse.ims.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration to generate beans from bean definition's.
 */
@Configuration
public class EventAPIConfig {

    /**
     * Creates RestTemplate bean.
     *
     * @return {@Link RestTemplate}
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
