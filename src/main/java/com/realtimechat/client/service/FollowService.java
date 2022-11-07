package com.realtimechat.client.service;

import com.realtimechat.client.domain.Follow;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.dto.request.FollowRequestDto;
import com.realtimechat.client.repository.FollowRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepository followRepository;

    @Transactional
    public String create(FollowRequestDto requestDto) {
        followRepository.save(requestDto.toEntity());
        return "success";
    }
    
    @Transactional
    public String delete(Integer id, Member member) {
        String message = "fail";
        Follow follow = followRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("데이터가 없습니다."));
        
        if (follow.getMember().getId().equals(member.getId())) { // 작성자만 삭제 가능
            followRepository.delete(follow);
            message = "success";
        }

        return message;
    }
}
