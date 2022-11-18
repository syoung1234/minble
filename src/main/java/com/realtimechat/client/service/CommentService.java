package com.realtimechat.client.service;

import com.realtimechat.client.domain.Comment;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Post;
import com.realtimechat.client.dto.request.CommentRequestDto;
import com.realtimechat.client.repository.CommentRepository;
import com.realtimechat.client.repository.PostRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 댓글 추가
    @Transactional
    public String save(CommentRequestDto commentRequestDto) {
        Post post = postRepository.findById(commentRequestDto.getPostId()).orElseThrow(() -> new IllegalArgumentException("error"));
        commentRequestDto.setPost(post);
        commentRepository.save(commentRequestDto.toEntity());
        return "success";
    }
    

    // 댓글 삭제
    @Transactional
    public String delete(Member member, Integer id) {
        String message = "fail";
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("error"));

        if (member.getId().equals(comment.getMember().getId())) {
            commentRepository.delete(comment);
            message = "success";
        }

        return message;
    }
}
