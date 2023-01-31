package com.realtimechat.client.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.realtimechat.client.domain.EmailToken;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.repository.EmailTokenRepository;
import com.realtimechat.client.repository.MemberRepository;
import com.realtimechat.client.util.MailHandler;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailTokenService {

    private final EmailTokenRepository emailTokenRepository;
    private final MemberRepository memberRepository;
    private final JavaMailSender javaMailSender;
    
    // 이메일 인증 토큰 생성
    public String createEmailToken(Member member, String receiverEmail) {
        Assert.notNull(member, "필수");
        Assert.hasText(receiverEmail, "필수");

        // 이메일 토큰 저장
        EmailToken emailToken = EmailToken.createEmailToken(member);
        emailTokenRepository.save(emailToken);

        // 이메일 전송
        try {
            MailHandler mailMessage = new MailHandler(javaMailSender);
            mailMessage.setTo(receiverEmail);
            mailMessage.setSubject("minble 회원가입 이메일 인증");
            String content = "<p>아래의 링크를 클릭하면 가입이 완료됩니다.</p> <a href='http://localhost:3000/confirm-email?token="+emailToken.getId()+"'>http://localhost:3000/confirm-email</a>";
            mailMessage.setText(content, true);
            mailMessage.setFrom("mible@mible.com");
            mailMessage.send();
        } catch(Exception e) {
            e.printStackTrace();
        }
        

        return emailToken.getId();
    }

    public Optional<EmailToken> findByIdAndExpirationDateAfterAndExpired(String emailTokenId) {
        Optional<EmailToken> emailToken = emailTokenRepository.findByIdAndExpirationDateAfterAndExpired(emailTokenId, LocalDateTime.now(), false);

        return emailToken;
    }

    // 이메일 인증 완료
    @Transactional
    public String confirmEmail(String token) {
        String message = "success";
        EmailToken emailToken = this.findByIdAndExpirationDateAfterAndExpired(token).orElse(null);
        if (emailToken == null) {
            // 존재하지 않거나 유효 하지 않은 토큰
            EmailToken existToken = emailTokenRepository.findById(token).orElse(null);
            if (existToken == null) { // 회원가입을 하지 않은 유저
                message = "fail";
            } else { // 회원가입은 했지만 이메일 인증을 하지 않아 토큰이 만료된 유저
                message = existToken.getMember().getEmail();
            }
        } else {
            Member member = memberRepository.findById(emailToken.getMember().getId()).orElse(null);
            emailToken.useToken(); // 이메일 인증으로 인한 토큰 만료 
            member.updateEmailConfirmation(true); // 이메일 인증된 유저 
        }

        return message;
    }
}