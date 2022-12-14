package com.realtimechat.client.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.realtimechat.client.domain.Comment;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Reply;
import com.realtimechat.client.dto.request.ReplyRequestDto;
import com.realtimechat.client.dto.response.ReplyResponseDto;
import com.realtimechat.client.repository.CommentRepository;
import com.realtimechat.client.repository.ReplyRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

    // 목록
    public Map<String, Object> list(Integer commentId, Pageable pageable) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        Page<Reply> reply = replyRepository.findByCommentOrderByCreatedAt(comment, pageable);
        List<ReplyResponseDto> response = reply.stream().map(ReplyResponseDto::new).collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("replyList", response);

        Map<String, Integer> pageList = new HashMap<>();
        pageList.put("page", reply.getNumber());
        pageList.put("totalPages", reply.getTotalPages());
        pageList.put("nextPage", pageable.next().getPageNumber());
        pageList.put("size", pageable.getPageSize());

        result.put("pageList", pageList);

        return result;
    }
    
    // 생성
    public String save(Member member, ReplyRequestDto replyRequestDto) {
        String message = "success";
        Comment comment = commentRepository.findById(replyRequestDto.getCommentId()).orElse(null);
        replyRequestDto.setMember(member);
        replyRequestDto.setComment(comment);

        replyRepository.save(replyRequestDto.toEntity());

        return message;
    }

    // 삭제
    public String delete(Member member, Integer replyId) {
        String message = "fail";
        Reply reply = replyRepository.findById(replyId).orElseThrow(() -> new IllegalArgumentException("error"));

        if (member.getId().equals(reply.getMember().getId())) {
            replyRepository.delete(reply);
            message = "success";
        }

        return message;

    }
}
