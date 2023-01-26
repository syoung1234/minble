package com.realtimechat.client.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.realtimechat.client.domain.EmailToken;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.repository.EmailTokenRepository;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailTokenService {

    private final EmailSenderService emailSenderService;
    private final EmailTokenRepository emailTokenRepository;
    
    // 이메일 인증 토큰 생성
    public String createEmailToken(Member member, String receiverEmail) {
        Assert.notNull(member, "필수");
        Assert.hasText(receiverEmail, "필수");

        // 이메일 토큰 저장
        EmailToken emailToken = EmailToken.createEmailToken(member);
        emailTokenRepository.save(emailToken);

        // 이메일 전송
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(receiverEmail);
        mailMessage.setSubject("회원가입 이메일 인증");
        mailMessage.setText("http://localhost:3000/confirm-email/token="+emailToken.getId());
        emailSenderService.sendEmail(mailMessage);

        return emailToken.getId();
    }

    public Optional<EmailToken> findByIdAndExpirationDateAfterAndExpired(String emailTokenId) {
        Optional<EmailToken> emailToken = emailTokenRepository.findByIdAndExpirationDateAfterAndExpired(emailTokenId, LocalDateTime.now(), false);

        return emailToken;
    }

  
}
