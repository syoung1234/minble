package com.realtimechat.client.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.UrlResource;

@Getter
@Setter
@NoArgsConstructor
public class PostFileResponseDto {
    private UrlResource urlResource;
    private String contentDisposition;

    public PostFileResponseDto(UrlResource urlResource, String contentDisposition) {
        this.urlResource = urlResource;
        this.contentDisposition = contentDisposition;
    }
}
