package com.realtimechat.client.dto.response.admin;

import java.time.format.DateTimeFormatter;

import com.realtimechat.client.domain.Member;

import lombok.Getter;

@Getter
public class AdminMemberResponseDto {
    private String profilePath;
    private String nickname;
    private String email;
    private String role;
    private String createdAt;

    public AdminMemberResponseDto(Member entity) {
        this.profilePath = entity.getProfilePath();
        this.nickname = entity.getNickname();
        this.email = entity.getEmail();
        this.role = roleType(entity.getRole().toString());
        this.createdAt = entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }

    public String roleType(String name) {
        String result;
        if (name.equals("ROLE_ADMIN")) {
            result = "관리자";
        } else if (name.equals("ROLE_STAR")) {
            result = "스타";
        } else if (name.equals("ROLE_SUBSCRIBER")) {
            result = "구독자";
        } else {
            result = "일반";
        }

        return result;
    }
}
