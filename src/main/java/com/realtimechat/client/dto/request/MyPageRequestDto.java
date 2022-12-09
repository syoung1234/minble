package com.realtimechat.client.dto.request;

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

    public MyPageRequestDto(String nickname, String password, String newPassword) {
        this.nickname = nickname;
        this.password = password;
        this.newPassword = newPassword;
    }

}
