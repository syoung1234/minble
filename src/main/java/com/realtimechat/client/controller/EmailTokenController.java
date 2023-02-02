package com.realtimechat.client.controller;

import java.util.Map;
import java.util.Optional;

import com.realtimechat.client.domain.Member;
import com.realtimechat.client.repository.MemberRepository;
import com.realtimechat.client.service.EmailTokenService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/email")
public class EmailTokenController {
    private final EmailTokenService emailTokenService;
    private final MemberRepository memberRepository;

    // 회원가입 이메일 인증 완료
    @GetMapping("/confirm")
    public ResponseEntity<String> confirmEmail(@RequestParam String token) {
        String message = emailTokenService.confirmEmail(token);

        return ResponseEntity.ok(message);
    }

    // 회원가입 이메일 재전송
    @PostMapping("/confirm")
    public ResponseEntity<String> resend(@RequestBody Map<String, String> body) {
        Member member = memberRepository.findByEmailAndSocial(body.get("email"), null).orElse(null);
        String receiverEmail = member.getEmail();
        emailTokenService.createEmailToken(member, receiverEmail, null);

        return ResponseEntity.ok("success");
    }

    // 비밀번호 찾기 이메일 전송
    @GetMapping("/password")
    public ResponseEntity<String> sendPasswordEmail(@RequestParam String email) {
        Optional<Member> member = memberRepository.findByEmailAndSocial(email, null);
        if (!member.isPresent()) { // 회원가입된 유저가 아님 
            return ResponseEntity.ok("empty");
        } else {
            emailTokenService.createEmailToken(member.get(), email, "password");
            return ResponseEntity.ok("success");
        }
    }

    // 비밀번호 재설정
    @PostMapping("/password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String password = body.get("password");

        String response = emailTokenService.resetPassword(token, password);
        return ResponseEntity.ok(response);
    }
}
