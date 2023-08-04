package com.msa.apigateway.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {
    public LoggingFilter() {
        super(Config.class);
    }


    @Override
    public GatewayFilter apply(Config config) {
        return new OrderedGatewayFilter((exchange, chain) -> {
            // Custom pre filter
            ServerHttpRequest request = exchange.getRequest();
            log.info("Logging Filter baseMessage : {}", config.getBaseMessage());

            if(config.isPreLogger()) {
                log.info("Logging Filter Start : request id => {}", request.getId());
            }

            return chain.filter(exchange).then(Mono.fromRunnable(() -> { // Webflux Mono 비동기 방식 단일 값을 전달 할 때 사용
                ServerHttpResponse response = exchange.getResponse();
                if(config.isPreLogger()) {
                    log.info("Logging Filter End : response code => {}", response.getStatusCode());
                }
            }));
        }, Ordered.LOWEST_PRECEDENCE); // 필터의 우선 순위를 지정할 수 있다.
    }

    @Getter
    @Setter
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
