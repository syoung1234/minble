package com.realtimechat.client.dto.response;

import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realtimechat.client.domain.Message;
import com.realtimechat.client.domain.MessageFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageDetailResponseDto {
    private String content;
    private String nickname;
    private String createdAt;
    private String channel;
    private String profilePath;
    private String filePath;
    private String filename;
    
    public MessageDetailResponseDto(Message entity) {
        this.content = entity.getContent();
        this.nickname = entity.getMember().getNickname();
        this.createdAt = entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        this.channel = entity.getChatRoom().getChannel();
        this.profilePath = entity.getMember().getProfilePath();
        this.filePath = filePath(entity.getMessageFile());
        this.filename = filename(entity.getMessageFile());
    }

    public String filePath(MessageFile messageFile) {
        if (messageFile != null) {
            return messageFile.getFilePath(); 
        }
        return null;
    }

    public String filename(MessageFile messageFile) {
        if (messageFile != null) {
            return messageFile.getFilename(); 
        }
        return null;
    }
}
