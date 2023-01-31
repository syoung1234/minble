package com.realtimechat.client.controller;

import java.util.Map;

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
@RequestMapping("/api/email/confirm")
public class EmailTokenController {
    private final EmailTokenService emailTokenService;
    private final MemberRepository memberRepository;

    @GetMapping
    public ResponseEntity<String> confirmEmail(@RequestParam String token) {
        String message = emailTokenService.confirmEmail(token);

        return ResponseEntity.ok(message);
    }

    @PostMapping
    public ResponseEntity<String> resend(@RequestBody Map<String, String> body) {
        Member member = memberRepository.findByEmailAndSocial(body.get("email"), null).orElse(null);
        String receiverEmail = member.getEmail();
        emailTokenService.createEmailToken(member, receiverEmail);

        return ResponseEntity.ok("success");
    }
}
