package com.realtimechat.client.controller;

import java.util.List;
import java.util.Map;

import com.realtimechat.client.config.security.SecurityUser;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Role;
import com.realtimechat.client.dto.request.SocialRegisterRequestDto;
import com.realtimechat.client.dto.response.LoginResponseDto;
import com.realtimechat.client.dto.response.MemberResponseDto;
import com.realtimechat.client.repository.MemberRepository;
import com.realtimechat.client.service.EmailTokenService;
import com.realtimechat.client.service.MemberService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final EmailTokenService emailTokenService;


    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody Map<String, String> user) {
        LoginResponseDto response = memberService.login(user);
        
        return ResponseEntity.ok(response);
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> user) {
        Member member = memberRepository.save(Member.builder()
                .email(user.get("email"))
                .password(passwordEncoder.encode(user.get("password")))
                .nickname(user.get("nickname"))
                .phone(user.get("phone"))
                .role(Role.ROLE_MEMBER)
                .build());
        
        emailTokenService.createEmailToken(member, user.get("email"), null);
        return ResponseEntity.ok("success");
    } 


    // 소셜 회원가입
    @PostMapping("/register/social")
    public String social(@RequestBody SocialRegisterRequestDto socialRegisterRequestDto) {
        return memberService.socialSave(socialRegisterRequestDto);
    }

    
    // 이메일, 닉네임 중복 확인
    @PostMapping("duplicate/{type}")
    public String duplicate(@PathVariable String type, @RequestBody Map<String, String> user) {
        String message = "exist";
        Member member = null;
        if (type.equals("email")) {
            member = memberRepository.findByEmailAndSocial(user.get("email"), null).orElse(null);
        
        } else if (type.equals("nickname")) {
            member = memberRepository.findByNickname(user.get("nickname")).orElse(null);
        }

        if (member == null) {
            message = "available";
        }
        
        return message;
    }


    // 팔로우 리스트 
    @GetMapping("/follow/list")
    public ResponseEntity<List<MemberResponseDto>>followList(@AuthenticationPrincipal SecurityUser principal) {
        Member member = null;
        
        if (principal != null) { // 로그인 한 유저 
            member = principal.getMember();
        } 

        List<MemberResponseDto> response = memberService.followList(member);
        return ResponseEntity.ok(response);
    }
    
}
