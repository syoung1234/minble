package com.realtimechat.client.config.security;



import com.realtimechat.client.config.oauth.CustomOAuth2UserService;
import com.realtimechat.client.config.oauth.OAuth2SuccessHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler successHandler;

    // 비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // authenticationManager를 Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
        

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable() // rest api 만을 고려하여 기본설정 해제
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 사용 안함
            .and()
            .authorizeRequests() // 요청에 대한 사용 권한 체크
            .antMatchers("/api/admin/**").hasRole("ADMIN")
            .antMatchers("/api/post/**").authenticated()
            .antMatchers("/api/mypage/**").authenticated()
            .anyRequest().permitAll() // 그외 나머지 요청은 누구나 접근 가능
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
            .oauth2Login()
            .successHandler(successHandler)
            .loginPage("/api/refresh-token")
            .userInfoEndpoint()
            .userService(customOAuth2UserService);
            // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣음

        return http.build();
    }
    
}
