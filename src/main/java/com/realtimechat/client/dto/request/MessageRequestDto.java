package com.realtimechat.client.dto.request;

import com.realtimechat.client.domain.ChatRoom;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Message;
import com.realtimechat.client.domain.MessageFile;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class MessageRequestDto {
    private String nickname;
    private String content;
    private String channel;
    private String profilePath;
    private Member member;
    private ChatRoom chatRoom;
    private String filePath;
    private MessageFile messageFile;

    @Builder
    public MessageRequestDto(Member member, ChatRoom chatRoom, String content) {
        this.member = member;
        this.chatRoom = chatRoom;
        this.content = content;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public void setMessageFile(MessageFile messageFile) {
        this.messageFile = messageFile;
    }


    public Message toEntity() {
        return Message.builder()
                .member(member)
                .chatRoom(chatRoom)
                .content(content)
                .messageFile(messageFile)
                .build();
    }
}
