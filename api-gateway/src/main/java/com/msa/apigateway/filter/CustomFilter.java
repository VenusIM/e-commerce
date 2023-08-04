package com.msa.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    public CustomFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            // Custom pre filter
            ServerHttpRequest request = exchange.getRequest();
            log.info("Custom PRE filter : request id => {}", request.getId());

            return chain.filter(exchange).then(Mono.fromRunnable(() -> { // Webflux Mono 비동기 방식 단일 값을 전달 할 때 사용
                ServerHttpResponse response = exchange.getResponse();
                log.info("Custom Post filter : response code => {}", response.getStatusCode());
            }));
        };
    }


    public static class Config {

    }

}
