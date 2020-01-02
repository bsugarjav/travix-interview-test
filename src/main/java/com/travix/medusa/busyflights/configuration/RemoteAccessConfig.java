package com.travix.medusa.busyflights.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RemoteAccessConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
