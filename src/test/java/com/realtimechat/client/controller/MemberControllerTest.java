package com.realtimechat.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Role;
import com.realtimechat.client.dto.request.member.DuplicateRequestDto;
import com.realtimechat.client.dto.request.member.LoginRequestDto;
import com.realtimechat.client.dto.request.member.RegisterRequestDto;
import com.realtimechat.client.dto.response.LoginResponseDto;
import com.realtimechat.client.exception.ErrorCode;
import com.realtimechat.client.exception.MemberException;
import com.realtimechat.client.service.EmailTokenService;
import com.realtimechat.client.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
// @AutoConfigureMockMvc(addFilters = false)
class MemberControllerTest {

    @MockBean
    private MemberService memberService;

    @MockBean
    private EmailTokenService emailTokenService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final String email = "test222@test.com";
    private final String nickname = "test222";
    private final String password = "1234";

    @Test
    void exist() {
        assertThat(mockMvc).isNotNull();
    }

    @DisplayName("중복되는 이메일 없음")
    @Test
    @WithMockUser
    void duplicate_x() throws Exception {
        // given
        String url = "/api/duplicate/email";
        doReturn("available").when(memberService).duplicate(any(DuplicateRequestDto.class), eq("email"));

        // when
        DuplicateRequestDto duplicateRequestDto = new DuplicateRequestDto(email, null);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .content(objectMapper.writeValueAsString(duplicateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().string("available"));
    }

    @DisplayName("중복되는 이메일 있음")
    @Test
    @WithMockUser
    void duplicate_o() throws Exception {
        // given
        String url = "/api/duplicate/email";
        doThrow(new MemberException(ErrorCode.DUPLICATED_MEMBER))
                .when(memberService).duplicate(any(DuplicateRequestDto.class), eq("email"));

        // when
        DuplicateRequestDto duplicateRequestDto = new DuplicateRequestDto(email, null);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .content(objectMapper.writeValueAsString(duplicateRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
        );

        // then
        result.andExpect(status().isBadRequest());
    }

    @DisplayName("회원가입")
    @Test
    @WithMockUser
    void register() throws Exception {
        // given
        String url = "/api/register";
        Member member = new Member();
        doReturn(member).when(memberService).register(any(RegisterRequestDto.class));

        // when
        RegisterRequestDto registerRequestDto = new RegisterRequestDto(email, password, nickname);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequestDto))
                .with(csrf())
        );

        // then
        result.andExpect(status().isOk());
    }

    @DisplayName("로그인 - 이메일 또는 비밀번호 틀림")
    @Test
    @WithMockUser
    void login_not_found() throws Exception {
        // given
        String url = "/api/login";
        doThrow(new MemberException(ErrorCode.MEMBER_NOT_FOUND)).when(memberService).login(any(LoginRequestDto.class));

        // when
        LoginRequestDto loginRequestDto = new LoginRequestDto(email, password);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto))
                .with(csrf())
        );

        // then
        result.andExpect(status().isNotFound());
    }

    @DisplayName("로그인 - 이메일 인증 x")
    @Test
    @WithMockUser
    void login_email_confirm() throws Exception {
        // given
        String url = "/api/login";
        doThrow(new MemberException(ErrorCode.UNAUTHORIZED_MEMBER)).when(memberService).login(any(LoginRequestDto.class));

        // when
        LoginRequestDto loginRequestDto = new LoginRequestDto(email, password);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto))
                .with(csrf())
        );

        // then
        result.andExpect(status().isUnauthorized());
    }

    @DisplayName("로그인 성공")
    @Test
    @WithMockUser
    void login() throws Exception {
        // given
        String url = "/api/login";
        LoginResponseDto loginResponseDto = new LoginResponseDto(null, null, "success", Role.ROLE_MEMBER.toString(), nickname);
        doReturn(loginResponseDto).when(memberService).login(any(LoginRequestDto.class));

        // when
        LoginRequestDto loginRequestDto = new LoginRequestDto(email, password);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto))
                .with(csrf())
        );

        // then
        result.andExpect(status().isOk());

        String jsonResponse = result.andReturn().getResponse().getContentAsString();
        loginResponseDto = objectMapper.readValue(jsonResponse, LoginResponseDto.class);

        assertThat(loginResponseDto.getStatus()).isEqualTo("success");
    }
}