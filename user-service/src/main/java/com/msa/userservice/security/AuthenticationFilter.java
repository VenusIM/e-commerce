package com.msa.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.userservice.dto.UserDTO;
import com.msa.userservice.service.UserService;
import com.msa.userservice.vo.request.RequestLogin;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.server.Cookie;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;
    private final Environment environment;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        RequestLogin credential;

        try {
            credential = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        credential.getEmail(),
                        credential.getPassword(),
                        new ArrayList<>()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String userName = ((User) authResult.getPrincipal()).getUsername();
        UserDTO userDetails = userService.getUserDetailByEmail(userName);


        String tokenCookie = ResponseCookie.from("token",
                        Jwts.builder()
                                .setSubject(userDetails.getUserId())
                                .setExpiration(new Date(System.currentTimeMillis() +
                                        Long.parseLong(environment.getProperty("token.expiration-time"))
                                ))
                                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(environment.getProperty("token.secret-key"))), SignatureAlgorithm.HS512)
                                .compact()
                )
                .path("/")
                .httpOnly(true)
                .sameSite(Cookie.SameSite.NONE.name())
                .build()
                .toString();

        String userIdCookie = ResponseCookie.from("userId", userDetails.getUserId())
                .path("/")
                .httpOnly(true)
                .sameSite(Cookie.SameSite.NONE.name())
                .build()
                .toString();

        response.addHeader(HttpHeaders.SET_COOKIE, tokenCookie);
        response.addHeader(HttpHeaders.SET_COOKIE, userIdCookie);
    }
}
