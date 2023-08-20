package com.realtimechat.client.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostFileResponseDto {
    private String fileName;
    private String filePath;

    public PostFileResponseDto(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }
}
