package com.realtimechat.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realtimechat.client.config.security.SecurityUser;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Role;
import com.realtimechat.client.dto.request.FavoriteRequestDto;
import com.realtimechat.client.dto.response.FavoriteResponseDto;
import com.realtimechat.client.service.FavoriteService;
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
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static reactor.core.publisher.Mono.when;

@WebMvcTest(FavoriteController.class)
class FavoriteControllerTest {

    @MockBean
    private FavoriteService favoriteService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("좋아요 저장/삭제")
    @Test
    void save() throws Exception {
        // given
        String url = "/api/favorite";
        FavoriteRequestDto favoriteRequestDto = new FavoriteRequestDto();

        Member member = new Member();
        member.setId(UUID.randomUUID());
        member.setEmail("test10@test.com");
        member.setNickname("test10");
        member.setRole(Role.ROLE_MEMBER);
        SecurityUser principal = new SecurityUser(member);

        FavoriteResponseDto favoriteResponseDto = new FavoriteResponseDto();
        doReturn(favoriteResponseDto).when(favoriteService).save(favoriteRequestDto, member);

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url).
                contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(favoriteRequestDto))
                .with(csrf())
                .with(user(principal)));

        // then
        result.andExpect(status().isOk());
    }
}