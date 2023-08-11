package com.realtimechat.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realtimechat.client.config.security.SecurityUser;
import com.realtimechat.client.domain.Comment;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Role;
import com.realtimechat.client.dto.request.CommentRequestDto;
import com.realtimechat.client.dto.response.CommentResponseDto;
import com.realtimechat.client.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @MockBean
    private CommentService commentService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("댓글 조회")
    @Test
    void find() throws Exception {
        // given
        String url = "/api/comment";
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        String postId = "1";
        Pageable pageable = PageRequest.of(0, 10);

        Member member = new Member();
        member.setId(UUID.randomUUID());
        member.setEmail("test10@test.com");
        member.setNickname("test10");
        member.setRole(Role.ROLE_MEMBER);

        Comment comment1 = new Comment();
        comment1.setMember(member);
        comment1.setContent("content");

        Comment comment2 = new Comment();
        comment2.setMember(member);

        List<CommentResponseDto> list = Arrays.asList(
                new CommentResponseDto(comment1),
                new CommentResponseDto(comment2)
        );
        Page<CommentResponseDto> page = new PageImpl<>(list, pageable, list.size());
        SecurityUser principal = new SecurityUser(member);
        doReturn(page).when(commentService).find(Integer.valueOf(postId), pageable);

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .param("postId", postId)
                .content(objectMapper.writeValueAsString(commentRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(principal)));

        // then
        result.andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("댓글 저장")
    @Test
    void save() throws Exception {
        // given
        String url ="/api/comment";
        CommentRequestDto commentRequestDto = new CommentRequestDto("test", 1, null, 0);
        Member member = new Member();
        member.setId(UUID.randomUUID());
        member.setEmail("test10@test.com");
        member.setNickname("test10");
        member.setRole(Role.ROLE_MEMBER);

        SecurityUser principal = new SecurityUser(member);
        doReturn("success").when(commentService).save(commentRequestDto, member);

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .content(objectMapper.writeValueAsString(commentRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .with(user(principal)));

        // then
        result.andExpect(status().isOk());
    }
}