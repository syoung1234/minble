package com.realtimechat.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realtimechat.client.config.security.SecurityUser;
import com.realtimechat.client.domain.Follow;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Role;
import com.realtimechat.client.dto.request.FollowRequestDto;
import com.realtimechat.client.service.FollowService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FollowController.class)
class FollowControllerTest {

    @MockBean
    private FollowService followService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("팔로우 저장")
    @Test
    void save() throws Exception {
        // given
        String url = "/api/follow/create";
        FollowRequestDto followRequestDto = new FollowRequestDto();
        Member member = new Member();
        member.setId(UUID.randomUUID());
        member.setEmail("test10@test.com");
        member.setRole(Role.ROLE_MEMBER);

        SecurityUser principal = new SecurityUser(member);
        doReturn(new Follow()).when(followService).save(any(FollowRequestDto.class), any(Member.class));

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .content(objectMapper.writeValueAsString(followRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(principal))
                .with(csrf()));

        // then
        result.andExpect(status().isOk());
    }

    @DisplayName("팔로우 삭제")
    @Test
    void delete() throws Exception {
        // given
        String nickname = "star1";
        String url = "/api/follow/{nickname}/delete";
        Member member = new Member();
        member.setId(UUID.randomUUID());
        member.setEmail("test10@test.com");
        member.setRole(Role.ROLE_MEMBER);
        SecurityUser principal = new SecurityUser(member);

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete(url, nickname)
                .with(user(principal))
                .with(csrf()));

        // then
        result.andExpect(status().isOk());
        result.andExpect(content().string("success"));
    }
}