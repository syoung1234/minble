package com.realtimechat.client.config.oauth;

import java.util.Map;

import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuth2Attributes {
    private Map<String, Object> attributes; // OAtuh2 반환하는 유저 정보 Map
    private String nameAttributeKey;
    private String nickname;
    private String email;
    private String social;

    @Builder
    public OAuth2Attributes(Map<String, Object> attributes, String nameAttributeKey, String nickname, String email, String social) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.nickname = nickname;
        this.email = email;
        this.social = social;
    }

    public static OAuth2Attributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        System.out.println(registrationId);
        if (registrationId.equals("kakao")) {
        }
        return ofKakao(userNameAttributeName, attributes);
    }
    
    public static OAuth2Attributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        System.out.println(attributes);
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuth2Attributes.builder()
            .nickname((String) kakaoProfile.get("nickname"))
            .email((String) kakaoAccount.get("email"))
            .social("kakao")
            .nameAttributeKey(userNameAttributeName)
            .attributes(attributes)
            .build();
    }

    public Member toEntity() {
        return Member.builder()
                .nickname(nickname)
                .email(email)
                .social(social)
                .role(Role.ROLE_MEMBER)
                .build();
    }
}