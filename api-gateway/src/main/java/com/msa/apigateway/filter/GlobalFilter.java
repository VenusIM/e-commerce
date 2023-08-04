package com.msa.apigateway.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    public GlobalFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            // Custom pre filter
            ServerHttpRequest request = exchange.getRequest();
            log.info("Global Filter baseMessage : {}", config.getBaseMessage());

            if(config.isPreLogger()) {
                log.info("Global Filter Start : request id => {}", request.getId());
            }

            return chain.filter(exchange).then(Mono.fromRunnable(() -> { // Webflux Mono 비동기 방식 단일 값을 전달 할 때 사용
                ServerHttpResponse response = exchange.getResponse();
                if(config.isPreLogger()) {
                    log.info("Global Filter End : response code => {}", response.getStatusCode());
                }
            }));
        };
    }

    @Getter
    @Setter
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }

}
