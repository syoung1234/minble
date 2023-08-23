package com.minble.client.dto.request;

import java.util.List;

import com.minble.client.domain.Member;
import com.minble.client.domain.Post;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostRequestDto {

    private String content;
    private Member member;
    private List<MultipartFile> files;
    private List<String> deleteList;

    @Builder
    public PostRequestDto(String content, Member member, List<MultipartFile> files, List<String> deleteList) {
        this.content = content;
        this.member = member;
        this.files = files;
        this.deleteList = deleteList;
    }

    public Post toEntity() {
        return Post.builder()
                .content(content)
                .member(member)
                .build();
    }
    
}
