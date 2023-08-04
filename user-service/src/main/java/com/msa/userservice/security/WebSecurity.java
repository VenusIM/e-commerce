package com.msa.userservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class WebSecurity {

    /*
    https://github.com/jzheaux/cve-2023-34035-mitigations
    */
    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector).servletPath("/");
    }

    /*
    WebSecurityConfigurerAdapter Deprecated 스프링 시큐리티에서 권장하는 방식
    https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity
//            , MvcRequestMatcher.Builder mvc
    ) throws Exception {
        httpSecurity.cors().and().csrf().disable()
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(mvc.pattern("user/**")).authenticated()
                                .anyRequest().permitAll())
                .headers().frameOptions().disable();
        return httpSecurity.build();
    }
}
