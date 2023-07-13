package com.realtimechat.client.dto.request.member;

import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Role;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

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
