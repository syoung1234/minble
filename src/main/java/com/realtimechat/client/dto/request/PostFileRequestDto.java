package com.realtimechat.client.dto.request;

import com.realtimechat.client.domain.Post;
import com.realtimechat.client.domain.PostFile;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostFileRequestDto {
    private Post post;
    private String filename;
    private String originalFileName;
    private String filePath;
    private Long fileSize;
    private String fileType;


    @Builder
    public PostFileRequestDto(Post post, String filename, String originalFileName, String filePath, Long fileSize, String fileType) {
        this.post = post;
        this.filename = filename;
        this.originalFileName = originalFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.fileType = fileType;
    }

    public PostFile toEntity() {
        return PostFile.builder()
                .post(post)
                .filename(filename)
                .originalFileName(originalFileName)
                .filePath(filePath)
                .fileSize(fileSize)
                .fileType(fileType)
                .build();
    }
}
