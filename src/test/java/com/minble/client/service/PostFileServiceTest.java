package com.minble.client.service;

import com.minble.client.domain.PostFile;
import com.minble.client.dto.response.PostFileDownloadResponseDto;
import com.minble.client.repository.PostFileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
        PostFileDownloadResponseDto result = postFileService.download(filename);

        // then
        assertThat(result).isNotNull();

    }
}