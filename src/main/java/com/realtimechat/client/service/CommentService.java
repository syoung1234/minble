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
import com.realtimechat.client.exception.CommentException;
import com.realtimechat.client.exception.ErrorCode;
import com.realtimechat.client.exception.PostException;
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

    /**
     * 댓글 조회
     * @param postId 게시글 Id
     * @param pageable 페이지
     * @return Page<CommentResponse>
     */
    public Page<CommentResponseDto> find(Integer postId, Pageable pageable) {
        Page<Comment> comments = commentRepository.findByPostId(postId, pageable);
        return comments.map(CommentResponseDto::new);
    }

    /**
     * 댓글 및 답글 저장
     * @param commentRequestDto (Member member, Post post, String content, Integer postId, Integer parentId, Integer depth)
     * @param member 댓글/답글 작성자
     * @return
     */
    @Transactional
    public String save(CommentRequestDto commentRequestDto, Member member) {
        commentRequestDto.setMember(member);
        Post post = postRepository.findById(commentRequestDto.getPostId()).orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND));
        commentRequestDto.setPost(post);

        if (commentRequestDto.getParentId() != null) { // 답글일 경우
            Comment comment = commentRepository.findById(commentRequestDto.getParentId()).orElseThrow(()
                    -> new CommentException(ErrorCode.COMMENT_NOT_FOUND));
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
