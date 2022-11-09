package com.realtimechat.client.dto.request;

import java.util.List;

import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Post;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostRequestDto {

    private String content;
    private Member member;
    private List<MultipartFile> files;

    @Builder
    public PostRequestDto(String content, Member member, List<MultipartFile> files) {
        this.content = content;
        this.member = member;
        this.files = files;
    }

    public Post toEntity() {
        return Post.builder()
                .content(content)
                .member(member)
                .build();
    }
    
}
