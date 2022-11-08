package com.realtimechat.client.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Post;
import com.realtimechat.client.dto.request.PostRequestDto;
import com.realtimechat.client.dto.response.FollowResponseDto;
import com.realtimechat.client.dto.response.PostResponseDto;
import com.realtimechat.client.repository.FollowRepository;
import com.realtimechat.client.repository.PostRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * Name : PostService.java
 * Description : 포스트 CRUD
 * author syoung1234
 * email 
 * since 2022.11.07
 *
 *  
 **/
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final FollowRepository followRepository;

    // list
    public Map<String, Object> list(Member member) {
        List<FollowResponseDto> follow = followRepository.findByMember(member);
        List<Map<String,Object>> listmap = new ArrayList<Map<String, Object>>();
        Map<String, Object> followingList = new HashMap<String, Object>();

        // 팔로잉한 member 찾은 후 nickname, profile만 보여줌 
        for (FollowResponseDto following : follow) {
            followingList.put("nickname", following.getFollowing().getNickname());
            followingList.put("profile", following.getFollowing().getProfile_path());
            listmap.add(followingList);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", listmap);

        return result;
    }

    // get
    public PostResponseDto find(Integer id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        return new PostResponseDto(post);
    }

    // create
    @Transactional
    public Post save(PostRequestDto requestDto) {
        return postRepository.save(requestDto.toEntity());
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
