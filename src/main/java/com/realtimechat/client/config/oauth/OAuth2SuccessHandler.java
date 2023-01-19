package com.realtimechat.client.config.oauth;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.realtimechat.client.config.security.JwtTokenProvider;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.repository.MemberRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
        throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        
        String targetUrl;
        UriComponents uriComponents;

        Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");

        Member member = memberRepository.findByEmail(kakaoAccount.get("email").toString()).orElse(null);

        if (member == null) {
            // 신규 회원가입
            uriComponents = UriComponentsBuilder.newInstance().scheme("http").host("localhost:3000/create/nickname")
            .queryParam("email", kakaoAccount.get("email")).queryParam("social", "kakao").build();
        } else {
            // 로그인
            String token = jwtTokenProvider.createToken(member.getEmail(), member.getRole());

            uriComponents = UriComponentsBuilder.newInstance().scheme("http").host("localhost:3000/start")
            .queryParam("accessToken", token).build();
        }

        targetUrl = uriComponents.toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
