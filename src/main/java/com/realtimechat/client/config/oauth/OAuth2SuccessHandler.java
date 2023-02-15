package com.realtimechat.client.config.oauth;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.realtimechat.client.config.security.JwtTokenProvider;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Value;
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

    @Value("${web.url}")
    private String webUrl;

    @Value("${default.profile.path}")
    private String defaultProfilePath;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
        throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        String targetUrl;
        UriComponents uriComponents;
        String social = null;
        String email = null;
        Map<String, Object> account = null;

        if (oAuth2User.getAttributes().get("kakao_account") != null) { // 카카오
            social = "kakao";
            account = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
        } else if (oAuth2User.getAttributes().get("response") != null) { // 네이버
            social = "naver";
            account = (Map<String, Object>) oAuth2User.getAttributes().get("response");
        } else {
            social = "google";
            account = (Map<String, Object>) oAuth2User.getAttributes();
        }

        email = account.get("email").toString();

        Member member = memberRepository.findByEmailAndSocialAndEmailConfirmation(email, social, true).orElse(null);

        if (member == null) { // 신규 회원가입
            Member socialMember = memberRepository.findByEmailAndSocial(email, social).orElse(null);
            socialMember.updateEmailConfirmation(true);
            socialMember.updateProfile(defaultProfilePath);
            memberRepository.save(socialMember);

            // uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(webUrl+"/create/nickname")
            // .queryParam("email", email).queryParam("social", social).build();

            // 2022.02.05 webUrl 추가로 인한 코드 수정 
            uriComponents = UriComponentsBuilder.fromUriString(webUrl+"/create/nickname")
            .queryParam("email", email).queryParam("social", social).queryParam("nickname", socialMember.getNickname()).build();
        } else { // 로그인
            String token = jwtTokenProvider.createToken(member.getNickname(), member.getRole(), member.getSocial());

            // uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(webUrl+"/start")
            // .queryParam("accessToken", token).build();

            // 2022.02.05 webUrl 추가로 인한 코드 수정 
            uriComponents = UriComponentsBuilder.fromUriString(webUrl+"/start").queryParam("accessToken", token).build();
        }

        targetUrl = uriComponents.toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
