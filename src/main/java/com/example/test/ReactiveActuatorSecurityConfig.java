package com.example.test;

import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class ReactiveActuatorSecurityConfig {
    @Bean
    public SecurityWebFilterChain actuatorSpringSecurityFilterChain(ServerHttpSecurity http) {
        EndpointRequest.EndpointServerWebExchangeMatcher actuatorEndpointMatcher = EndpointRequest.toAnyEndpoint();
        return http
                .securityMatcher(actuatorEndpointMatcher)
                .requestCache(ServerHttpSecurity.RequestCacheSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                        .pathMatchers(HttpMethod.POST, "/actuator/env").denyAll()
                        .pathMatchers(HttpMethod.DELETE, "/actuator/env").denyAll()
                        .anyExchange().permitAll()
                )
                .build();
    }
}
