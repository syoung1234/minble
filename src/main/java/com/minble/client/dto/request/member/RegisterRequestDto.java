package com.minble.client.dto.request.member;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
public class RegisterRequestDto {
    private String email;
    private String password;
    private String nickname;

    @Builder
    public RegisterRequestDto(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

}
