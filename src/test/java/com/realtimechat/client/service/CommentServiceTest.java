package com.realtimechat.client.service;

import com.realtimechat.client.domain.Comment;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.dto.response.CommentResponseDto;
import com.realtimechat.client.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @InjectMocks
    private CommentService commentService;
    @Mock
    private CommentRepository commentRepository;

    @Test
    void find() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        Member member = new Member();
        member.setNickname("test10");

        Comment comment = new Comment();
        comment.setId(1);
        comment.setMember(member);

        List<Comment> list = Arrays.asList(comment, comment);
        Page<Comment> comments = new PageImpl<>(list, pageable, list.size());
        doReturn(comments).when(commentRepository).findByPostId(1, pageable);

        // when
        Page<CommentResponseDto> result = commentService.find(1, pageable);

        // then
        assertThat(result).isNotNull();
        assertThat(result).size().isEqualTo(2);
    }

}