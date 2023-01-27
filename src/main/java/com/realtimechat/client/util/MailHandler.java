package com.realtimechat.client.util;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailHandler {
    private JavaMailSender javaMailSender;
    private MimeMessage mimeMessage;
    private MimeMessageHelper messageHelper;

    public MailHandler(JavaMailSender javaMailSender) throws MessagingException {
        this.javaMailSender = javaMailSender;
        mimeMessage = javaMailSender.createMimeMessage();
        messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
    }

    // 보내는 사람 이메일
    public void setFrom(String fromAddress) throws MessagingException {
        messageHelper.setFrom(fromAddress);
    }
    
    // 받는 사람 이메일
    public void setTo(String email) throws MessagingException {
        messageHelper.setTo(email);
    }

    // 제목
    public void setSubject(String subject) throws MessagingException {
        messageHelper.setSubject(subject);
    }

    // 메일 내용
    public void setText(String text, boolean useHtml) throws MessagingException {
        messageHelper.setText(text, useHtml);
    }

    // 첨부 파일
    public void setAttach(String fileName, String pathToAttachment) throws MessagingException, IOException {
        File file = new ClassPathResource(pathToAttachment).getFile();
        FileSystemResource fileSystemResource = new FileSystemResource(file);

        messageHelper.addAttachment(fileName, fileSystemResource);
    }

    // 이미지 삽입
    public void setInline(String contentId, String pathToInline) throws MessagingException, IOException {
        File file = new ClassPathResource(pathToInline).getFile();
        FileSystemResource fileSystemResource = new FileSystemResource(file);

        messageHelper.addInline(contentId, fileSystemResource);
    }

    // 발송
    public void send() {
        try {
            javaMailSender.send(mimeMessage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
