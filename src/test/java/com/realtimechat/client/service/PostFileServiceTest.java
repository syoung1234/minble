package com.realtimechat.client.service;

import com.realtimechat.client.domain.PostFile;
import com.realtimechat.client.dto.response.PostFileResponseDto;
import com.realtimechat.client.repository.PostFileRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.MalformedURLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostFileServiceTest {

    @InjectMocks
    private PostFileService postFileService;
    @Mock
    private PostFileRepository postFileRepository;

    @Test
    void download() throws Exception {
        // given
        PostFile postFile = new PostFile();
        String filename = "test.png";
        when(postFileRepository.findByFilename(filename)).thenReturn(Optional.of(postFile));

        // when
        PostFileResponseDto result = postFileService.download(filename);

        // then
        assertThat(result).isNotNull();

    }
}