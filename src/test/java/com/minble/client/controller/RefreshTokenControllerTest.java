package com.minble.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minble.client.config.security.JwtTokenProvider;
import com.minble.client.service.RefreshTokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.Cookie;

import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RefreshTokenController.class)
class RefreshTokenControllerTest {

    @MockBean
    private RefreshTokenService refreshTokenService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("access token 발급")
    @Test
    @WithMockUser
    void getToken() throws Exception {
        // given
        String url = "/api/refresh-token";
        String refreshToken = "refresh-token-test";
        doReturn("access-token").when(refreshTokenService).getToken(refreshToken);

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("refreshToken", refreshToken))
                .content(objectMapper.writeValueAsString(refreshToken))
                .with(csrf()));

        // then
        result.andExpect(status().isOk());
    }

}