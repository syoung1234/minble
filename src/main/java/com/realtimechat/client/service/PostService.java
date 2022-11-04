package com.realtimechat.client.service;


import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Post;
import com.realtimechat.client.dto.request.PostRequestDto;
import com.realtimechat.client.dto.response.PostResponseDto;
import com.realtimechat.client.repository.PostRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    // list
    public Post list() {
        return null;
    }

    // get
    public PostResponseDto find(Integer id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        return new PostResponseDto(post);
    }

    // create
    @Transactional
    public Integer save(PostRequestDto requestDto) {
        return postRepository.save(requestDto.toEntity()).getId();
    }
    
    // update
    @Transactional
    public String update(Integer id, PostRequestDto requestDto) {
        String message = "fail";
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        // 작성자만 수정 가능 
        if (postRepository.findById(id).get().getMember().getId().equals(requestDto.getMember().getId())) {
            post.update(requestDto.getContent());
            message = "success";
        } 

        return message;

    }

    // delete
    @Transactional
    public String delete(Integer id, Member member) {
        String message = "fail";
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        
        if (post.getMember().getId().equals(member.getId())) { // 작성자만 삭제 가능
            postRepository.delete(post);
            message = "success";
        }

        return message;
        
    }

}
