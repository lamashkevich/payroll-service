package com.lamashkevich.security;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class InternalFeignConfig {

    private final ServiceAuthenticationProvider tokenProvider;

    @Bean
    public RequestInterceptor internalTokenInterceptor() {
        return requestTemplate -> {
            String token = tokenProvider.getToken();
            requestTemplate.header("Authorization", "Bearer " + token);
        };
    }

}
