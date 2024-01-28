package com.wefox.techtest.adapter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    private static final String HOST = "http://localhost:9000";

    @Bean(name = "paymentWebClient")
    public WebClient paymentWebClient() {
        return WebClient.create(HOST.concat("/payment"));
    }

    @Bean(name = "logWebClient")
    public WebClient logWebClient() {
        return WebClient.create(HOST.concat("/log"));
    }
}
