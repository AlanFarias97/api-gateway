package com.acme.platform.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Identity Service Routes
                .route("identity-service", r -> r
                        .path("/identity/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .circuitBreaker(config -> config
                                        .setName("identity-cb")
                                        .setFallbackUri("forward:/fallback/identity")))
                        .uri("lb://identity-svc"))
                
                // HR Service Routes  
                .route("hr-service", r -> r
                        .path("/hr/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .circuitBreaker(config -> config
                                        .setName("hr-cb")
                                        .setFallbackUri("forward:/fallback/hr")))
                        .uri("lb://hr-svc"))
                
                // Billing Service Routes
                .route("billing-service", r -> r
                        .path("/billing/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .circuitBreaker(config -> config
                                        .setName("billing-cb")
                                        .setFallbackUri("forward:/fallback/billing")))
                        .uri("lb://billing-svc"))
                
                // Direct actuator access for health checks
                .route("identity-actuator", r -> r
                        .path("/actuator/identity/**")
                        .filters(f -> f
                                .rewritePath("/actuator/identity/(?<segment>.*)", "/actuator/${segment}"))
                        .uri("lb://identity-svc"))
                
                .route("hr-actuator", r -> r
                        .path("/actuator/hr/**")
                        .filters(f -> f
                                .rewritePath("/actuator/hr/(?<segment>.*)", "/actuator/${segment}"))
                        .uri("lb://hr-svc"))
                
                .route("billing-actuator", r -> r
                        .path("/actuator/billing/**")
                        .filters(f -> f
                                .rewritePath("/actuator/billing/(?<segment>.*)", "/actuator/${segment}"))
                        .uri("lb://billing-svc"))
                
                .build();
    }
}
