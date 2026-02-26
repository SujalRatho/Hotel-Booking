package com.sujal.api_gateway.filter;

import com.sujal.api_gateway.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    @Autowired
    private Validator validator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // if true=> the request should be subject to authentication checks
            if (validator.predicate.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new BadRequestException("Missing Authorization header", HttpStatus.UNAUTHORIZED);
                }
                String authHeader=exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                String token = null;
                if(null!=authHeader && authHeader.startsWith("Bearer ")){
                    token= authHeader.substring(7);
                }
                try {
                    jwtUtil.validateToken(token);
                } catch (Exception e) {
                    throw new BadRequestException("Invalid token", HttpStatus.UNAUTHORIZED);
                }
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
        // Configuration properties for the filter can be added here
    }

}
