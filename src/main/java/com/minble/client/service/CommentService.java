package com.minble.client.service;

import com.minble.client.domain.Comment;
import com.minble.client.domain.Member;
import com.minble.client.domain.Post;
import com.minble.client.dto.request.CommentRequestDto;
import com.minble.client.dto.response.CommentResponseDto;
import com.minble.client.exception.CommentException;
import com.minble.client.exception.ErrorCode;
import com.minble.client.exception.PostException;
import com.minble.client.repository.CommentRepository;
import com.minble.client.repository.PostRepository;

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
        Page<Comment> comments = commentRepository.findByPostIdAndDepth(postId, 0, pageable);

        if (comments.isEmpty()) {
            throw new CommentException(ErrorCode.COMMENT_NOT_FOUND);
        }

        return comments.map(CommentResponseDto::new);
    }


    /**
     * 답글 더보기 (조회)
     * @param parentId 게시글 ID
     * @param pageable 페이지 정보
     * @return Page<CommentResponseDto>
     */
    public Page<CommentResponseDto> getChildren(Integer parentId, Pageable pageable) {
        Page<Comment> childrenList = commentRepository.findByParentId(parentId, pageable);

        if (childrenList.isEmpty()) {
            throw new CommentException(ErrorCode.COMMENT_NOT_FOUND);
        }

        return childrenList.map(CommentResponseDto::new);
    }


    /**
     * 댓글 및 답글 저장
     * @param commentRequestDto (Member member, Post post, String content, Integer postId, Integer parentId, Integer depth)
     * @param member 댓글/답글 작성자
     * @return success
     */
    @Transactional
    public String save(CommentRequestDto commentRequestDto, Member member) {
        Post post = postRepository.findById(commentRequestDto.getPostId()).orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND));

        Comment parent = null;
        if (commentRequestDto.getParentId() != null) { // 답글일 경우
            parent = commentRepository.findById(commentRequestDto.getParentId()).orElseThrow(()
                    -> new CommentException(ErrorCode.COMMENT_NOT_FOUND));
        }

        commentRepository.save(commentRequestDto.toEntity(member, post, parent));

        return "success";
    }


    /**
     * 댓글/답글 삭제
     * @param member 작성자
     * @param id 댓글/답글 ID
     * @return Exception or success
     */
    @Transactional
    public String delete(Member member, Integer id) {
        Comment comment = commentRepository.findByIdAndMember(id, member).orElseThrow(()
                -> new CommentException(ErrorCode.COMMENT_NOT_FOUND));

        commentRepository.delete(comment);

        return "success";
    }

}
