package com.sujal.api_gateway.filter;

import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.function.Predicate;

@Component
public class Validator {
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public static final List<String> endpoints = List.of(
            "/auth/register",
            "/auth/generate-token",
            "/auth/validate-token/**");

    public Predicate<ServerHttpRequest> predicate = serverHttpRequest -> {
        if (serverHttpRequest.getMethod() == HttpMethod.OPTIONS) {
            return false;
        }
        String requestPath = serverHttpRequest.getURI().getPath();
        return endpoints.stream()
                        .noneMatch(uri -> pathMatcher.match(uri, requestPath));
    };
}
