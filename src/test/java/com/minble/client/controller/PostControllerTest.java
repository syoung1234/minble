package com.minble.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minble.client.config.security.JwtTokenProvider;
import com.minble.client.config.security.SecurityUser;
import com.minble.client.domain.Member;
import com.minble.client.domain.Role;
import com.minble.client.dto.response.PostResponseDto;
import com.minble.client.service.PostService;
import com.minble.client.service.RefreshTokenService;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @MockBean
    private PostService postService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private RefreshTokenService refreshTokenService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("팔로잉한 멤버 게시글 전체 조회")
    @Test
    void list() throws Exception {
        // given
        String url = "/api/post";
        Pageable pageable = PageRequest.of(0, 10);
        List<PostResponseDto> list = Arrays.asList(
                new PostResponseDto("star1", "profilePath", 10,"게시글 내용!", LocalDateTime.now(),3, 4),
                new PostResponseDto(),
                new PostResponseDto()
        );
        Page<PostResponseDto> pages = new PageImpl<>(list, pageable, list.size());
        doReturn(pages).when(postService).list(any(Member.class), anyString(), any(Pageable.class));

        Member member = new Member();
        member.setId(UUID.randomUUID());
        member.setEmail("test10@test.com");
        member.setRole(Role.ROLE_MEMBER);

        SecurityUser principal = new SecurityUser(member);

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .with(user(principal)));

        // then
        result.andExpect(status().isOk());

    }


    @DisplayName("게시글 조회")
    @Test
    void find() throws Exception {
        String url = "/api/post/1";

        PostResponseDto postResponseDto = new PostResponseDto("star1", "profilePath", 10,"게시글 내용!", LocalDateTime.now(),3, 4);

        Member member = new Member();
        member.setId(UUID.randomUUID());
        member.setEmail("test10@test.com");
        member.setRole(Role.ROLE_MEMBER);
        SecurityUser principal = new SecurityUser(member);

        doReturn(postResponseDto).when(postService).find(1, member);

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .with(user(principal)));

        // then
        result.andExpect(status().isOk());
    }

    @DisplayName("게시글 생성")
    @Test
    @WithMockUser
    void save() throws Exception {
        // given
        String url = "/api/post/create";
        Member member = new Member();
        member.setId(UUID.randomUUID());
        member.setEmail("test10@test.com");
        member.setRole(Role.ROLE_MEMBER);
        SecurityUser principal = new SecurityUser(member);

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .with(csrf())
                .with(user(principal)));

        // then
        result.andExpect(status().isOk());
    }
}