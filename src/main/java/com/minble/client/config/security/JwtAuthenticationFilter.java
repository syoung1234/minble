package com.minble.client.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.minble.client.service.RefreshTokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        // httpServletResponse.setHeader("Access-Control-Allow-Origin", "localhost:3000");
        // httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        // httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me, Origin,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization, X-AUTH-TOKEN");
        // httpServletResponse.setHeader("Access-Control-Allow-Credentials",  "true");

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        
        // 헤더에서 JWT를 받아옴
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        // 유효한 토큰인지 확인
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옴
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // SecurityContext에 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } else if (token != null && !jwtTokenProvider.validateToken(token)) {
            // JWT 토큰이 만료된 경우
            String refreshToken = getRefreshTokenFromCookie(httpServletRequest);

            if (refreshToken != null && jwtTokenProvider.validateRefreshToken(refreshToken)) {
                // 유효한 리프레시 토큰이 있는 경우
                token = refreshTokenService.getToken(refreshToken); // 새로운 액세스 토큰 발급
                Authentication authentication = jwtTokenProvider.getAuthentication(token); // 새로운 토큰으로 인증
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, httpServletResponse);
    }

    private String getRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
