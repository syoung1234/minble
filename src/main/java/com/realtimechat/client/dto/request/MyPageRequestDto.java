package com.realtimechat.client.dto.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class MyPageRequestDto {

    private String nickname;
    private String password;
    private String newPassword;
    private MultipartFile profile;

    public MyPageRequestDto(String nickname, String password, String newPassword, MultipartFile profile) {
        this.nickname = nickname;
        this.password = password;
        this.newPassword = newPassword;
        this.profile = profile;
    }

}
