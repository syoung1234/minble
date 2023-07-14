package com.realtimechat.client.config.oauth;

import java.util.Collections;

import com.realtimechat.client.domain.Member;
import com.realtimechat.client.exception.MemberErrorCode;
import com.realtimechat.client.exception.MemberException;
import com.realtimechat.client.repository.MemberRepository;
import com.realtimechat.client.util.CreateNickname;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;
    // private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = service.loadUser(userRequest); // OAuth2 정보를 가져옴

        // OAuth2 서비스 id (구글, 카카오, 네이버)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // OAuth2 로그인 진행 시 키가 되는 필드 값(PK)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2UserService
        OAuth2Attributes attributes = OAuth2Attributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        Member member = saveOrUpdate(attributes); 

        
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(member.getRole().toString())), 
            attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    /**
     * 소셜 로그인 및 회원가입
     * @param attributes (social member)
     * @return member
     */
    private Member saveOrUpdate(OAuth2Attributes attributes) {
        CreateNickname createNickname = new CreateNickname();
        String randomNickname = createNickname.randomNickname();

        Member checkNickname = memberRepository.findByNickname(randomNickname).orElse(null);

        while (checkNickname != null) {
            randomNickname = createNickname.randomNickname();
            checkNickname = memberRepository.findByNickname(randomNickname).orElse(null);
        }

        attributes.setNickname(randomNickname);

        // 같은 이메일로 가입된 이력이 있으면 exception
        memberRepository.findByEmailAndSocialNot(attributes.getEmail(), attributes.getSocial())
                .orElseThrow(() -> new MemberException(MemberErrorCode.DUPLICATED_MEMBER));

        Member member = memberRepository.findByEmailAndSocial(attributes.getEmail(), attributes.getSocial())
            .orElse(attributes.toEntity());
        return memberRepository.save(member);
    }
    
}
