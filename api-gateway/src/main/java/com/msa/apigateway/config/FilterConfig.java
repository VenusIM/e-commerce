package com.msa.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class FilterConfig {

//    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(predicateSpec -> predicateSpec.path("/first-service/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec.addRequestHeader("first-request","first-request-header")
                                .addResponseHeader("first-response","first-response-header"))
                        .uri("http://127.0.0.1:8081")
                ).route(predicateSpec -> predicateSpec.path("/second-service/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec.addRequestHeader("second-request","second-request-header")
                                .addResponseHeader("second-response","second-response-header"))
                        .uri("http://127.0.0.1:8082")
                ).build();

    }
}
