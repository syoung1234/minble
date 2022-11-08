package com.realtimechat.client.service;

import javax.transaction.Transactional;

import com.realtimechat.client.dto.request.PostFileRequestDto;
import com.realtimechat.client.repository.PostFileRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostFileService {
    private final PostFileRepository postFileRepository;

    // 저장
    @Transactional
    public void save(PostFileRequestDto postFileRequestDto) {
        postFileRepository.save(postFileRequestDto.toEntity());
    }    
    
}
