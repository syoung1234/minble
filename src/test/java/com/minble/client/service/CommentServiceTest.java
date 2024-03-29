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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @InjectMocks
    private CommentService commentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostRepository postRepository;


    @DisplayName("댓글 조회 - 게시글 없음")
    @Test
    void find_x() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        Integer postId = 1;

        Page<Comment> comments = new PageImpl<>(Collections.emptyList());
        doReturn(comments).when(commentRepository).findByPostIdAndDepth(postId, 0, pageable);

        // when
        CommentException commentException = assertThrows(CommentException.class, () -> commentService.find(postId, pageable));

        // then
        assertThat(commentException.getErrorCode()).isEqualTo(ErrorCode.COMMENT_NOT_FOUND);
    }


    @DisplayName("댓글 조회")
    @Test
    void find() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        Integer postId = 1;
        Member member = new Member();
        member.setNickname("test10");

        Comment comment = new Comment();
        comment.setId(1);
        comment.setMember(member);

        List<Comment> list = Arrays.asList(comment, comment);
        Page<Comment> comments = new PageImpl<>(list, pageable, list.size());
        doReturn(comments).when(commentRepository).findByPostIdAndDepth(postId, 0, pageable);

        // when
        Page<CommentResponseDto> result = commentService.find(postId, pageable);

        // then
        assertThat(result).isNotNull();
        assertThat(result).size().isEqualTo(2);
    }

    @DisplayName("답글 더보기")
    @Test
    void getChildren() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        Integer parentId = 1;
        Member member = new Member();
        member.setNickname("test10");

        Comment comment = new Comment();
        comment.setId(1);
        comment.setMember(member);

        List<Comment> list = Arrays.asList(comment, comment);
        Page<Comment> page = new PageImpl<>(list, pageable, list.size());

        doReturn(page).when(commentRepository).findByParentId(parentId, pageable);

        // when
        Page<CommentResponseDto> result = commentService.getChildren(parentId, pageable);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTotalPages()).isEqualTo(1);

    }

    @DisplayName("저장 실패-게시글 없음")
    @Test
    void save_fail() {
        // given
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        Member member = new Member();
        doReturn(Optional.empty()).when(postRepository).findById(any());

        // when
        PostException postException = assertThrows(PostException.class, () -> commentService.save(commentRequestDto, member));

        // then
        assertThat(postException.getErrorCode()).isEqualTo(ErrorCode.POST_NOT_FOUND);

    }

    @DisplayName("답글 저장 실패 - 댓글 없음")
    @Test
    void reply_fail() {
        // given
        CommentRequestDto commentRequestDto = new CommentRequestDto("test", 1, 1, 2);
        Post post = new Post();
        Member member = new Member();
        doReturn(Optional.of(post)).when(postRepository).findById(any());
        doReturn(Optional.empty()).when(commentRepository).findById(any());

        // when
        CommentException commentException = assertThrows(CommentException.class, () -> commentService.save(commentRequestDto, member));

        // then
        assertThat(commentException.getErrorCode()).isEqualTo(ErrorCode.COMMENT_NOT_FOUND);
    }

    @DisplayName("저장 성공")
    @Test
    void save() {
        // given
        CommentRequestDto commentRequestDto = new CommentRequestDto("test", 1, null, 1);
        Member member = new Member();
        Post post = new Post();
        doReturn(Optional.of(post)).when(postRepository).findById(any());

        // when
        String result = commentService.save(commentRequestDto, member);

        // then
        assertThat(result).isEqualTo("success");
    }


    @DisplayName("댓글 삭제 실패 - 댓글 없음")
    @Test
    void delete_comment_not_found() {
        // given
        Member member = new Member();
        int id = 1;
        doReturn(Optional.empty()).when(commentRepository).findByIdAndMember(id, member);

        // when
        CommentException commentException = assertThrows(CommentException.class, () -> commentService.delete(member, id));

        // then
        assertThat(commentException.getErrorCode()).isEqualTo(ErrorCode.COMMENT_NOT_FOUND);
    }

    @DisplayName("댓글 삭제")
    @Test
    void delete() {
        // given
        Member member = new Member();
        Comment comment = new Comment();
        int id = 1;
        doReturn(Optional.of(comment)).when(commentRepository).findByIdAndMember(id, member);

        // when
        String result = commentService.delete(member, id);

        // then
        assertThat(result).isEqualTo("success");
    }

}