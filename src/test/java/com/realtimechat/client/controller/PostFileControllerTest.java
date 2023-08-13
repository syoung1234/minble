package com.realtimechat.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realtimechat.client.service.PostFileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostFileController.class)
class PostFileControllerTest {

    @MockBean
    private PostFileService postFileService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void download() throws Exception {
        // given
        String url = "/api/download/";
        String filename = "file.png";

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url, filename)
                .contentType(MediaType.MULTIPART_FORM_DATA));

        // then
        result.andExpect(status().isOk());

    }
}