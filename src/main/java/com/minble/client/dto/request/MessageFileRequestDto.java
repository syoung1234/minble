package com.minble.client.dto.request;

import com.minble.client.domain.Message;
import com.minble.client.domain.MessageFile;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageFileRequestDto {
    private String filename;
    private String originalFileName;
    private String filePath;
    private Long fileSize;
    private String fileType;

    @Builder
    public MessageFileRequestDto(Message message,String filename, String originalFileName, String filePath, Long fileSize, String fileType) {
        this.filename = filename;
        this.originalFileName = originalFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.fileType = fileType;
    }

    public MessageFile toEntity() {
        return MessageFile.builder()
                .filename(filename)
                .originalFileName(originalFileName)
                .filePath(filePath)
                .fileSize(fileSize)
                .fileType(fileType)
                .build();
    }
}
