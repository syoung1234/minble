package com.realtimechat.client.controller;

import java.util.List;
import java.util.Map;

import com.realtimechat.client.config.security.SecurityUser;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.dto.request.SocialRegisterRequestDto;
import com.realtimechat.client.dto.request.member.DuplicateRequestDto;
import com.realtimechat.client.dto.request.member.LoginRequestDto;
import com.realtimechat.client.dto.request.member.RegisterRequestDto;
import com.realtimechat.client.dto.response.LoginResponseDto;
import com.realtimechat.client.dto.response.MemberResponseDto;
import com.realtimechat.client.service.EmailTokenService;
import com.realtimechat.client.service.MemberService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;
    private final EmailTokenService emailTokenService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto response = memberService.login(loginRequestDto);
        
        ResponseCookie cookie = ResponseCookie.from("refreshToken", response.getRefreshToken())
            .maxAge(30 * 24 * 60 * 60) // 30일
            .httpOnly(true)
            .secure(true)
            .path("/")
            .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(response);
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto registerRequestDto) {
        Member member = memberService.register(registerRequestDto);
        emailTokenService.createEmailToken(member, member.getEmail(), null);
        return ResponseEntity.ok("success");
    } 

    // 소셜 회원가입
    @PostMapping("/register/social")
    public ResponseEntity<LoginResponseDto> social(@RequestBody SocialRegisterRequestDto socialRegisterRequestDto) {
        LoginResponseDto response = memberService.socialSave(socialRegisterRequestDto);
        return ResponseEntity.ok(response);
    }


    // 이메일, 닉네임 중복 확인
    @PostMapping("/duplicate/{type}")
    public ResponseEntity<String> duplicate(@PathVariable String type, @RequestBody DuplicateRequestDto duplicateRequestDto) {
        String message = memberService.duplicate(duplicateRequestDto, type);

        return ResponseEntity.ok(message);
    }

}
