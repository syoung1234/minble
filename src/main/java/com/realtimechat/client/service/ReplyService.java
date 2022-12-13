package com.realtimechat.client.service;

import com.realtimechat.client.domain.Comment;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.dto.request.ReplyRequestDto;
import com.realtimechat.client.repository.CommentRepository;
import com.realtimechat.client.repository.ReplyRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;
    
    public String save(Member member, ReplyRequestDto replyRequestDto) {
        String message = "success";
        System.out.println(replyRequestDto);
        Comment comment = commentRepository.findById(replyRequestDto.getCommentId()).orElse(null);
        replyRequestDto.setMember(member);
        replyRequestDto.setComment(comment);

        replyRepository.save(replyRequestDto.toEntity());

        return message;
    }
}
