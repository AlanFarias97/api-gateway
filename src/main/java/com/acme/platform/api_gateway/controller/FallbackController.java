package com.acme.platform.api_gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class FallbackController {
    
    @GetMapping("/fallback/hr")
    public Mono<ResponseEntity<Map<String, Object>>> hrFallback() {
        var body = Map.<String, Object>of(
                "message", "HR-SVC no disponible",
                "timestamp", java.time.OffsetDateTime.now().toString());
        return Mono.just(ResponseEntity.status(503).body(body));
    }
    
    @GetMapping("/fallback/identity")
    public Mono<ResponseEntity<Map<String, Object>>> identityFallback() {
        var body = Map.<String, Object>of(
                "message", "Identity-SVC no disponible",
                "timestamp", java.time.OffsetDateTime.now().toString());
        return Mono.just(ResponseEntity.status(503).body(body));
    }
    
    @GetMapping("/fallback/billing")
    public Mono<ResponseEntity<Map<String, Object>>> billingFallback() {
        var body = Map.<String, Object>of(
                "message", "Billing-SVC no disponible",
                "timestamp", java.time.OffsetDateTime.now().toString());
        return Mono.just(ResponseEntity.status(503).body(body));
    }
}