package com.realtimechat.client.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.realtimechat.client.domain.Comment;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Post;
import com.realtimechat.client.dto.request.CommentRequestDto;
import com.realtimechat.client.dto.response.CommentResponseDto;
import com.realtimechat.client.repository.CommentRepository;
import com.realtimechat.client.repository.PostRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        if (commentRequestDto.getParentId() != null) { // 답글일 경우
            Comment comment = commentRepository.findById(commentRequestDto.getParentId()).orElse(null);
            commentRequestDto.setParent(comment);
        }

        // 저장
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

    // 답글 더보기
    public Map<String, Object> getChildren(Integer parentId, Pageable pageable) {
        Page<Comment> childrenList = commentRepository.findByParentId(parentId, pageable);

        List<CommentResponseDto> commentList = childrenList.stream().map(CommentResponseDto::new).collect(Collectors.toList());

        Map<String, Integer> pageList = new HashMap<>();
        pageList.put("page", childrenList.getNumber());
        pageList.put("totalPages", childrenList.getTotalPages());
        pageList.put("nextPage", pageable.next().getPageNumber());
        pageList.put("size", pageable.getPageSize());


        Map<String, Object> result = new HashMap<>();
        result.put("replyList", commentList);
        result.put("pageList", pageList);

        return result;
    }
}
