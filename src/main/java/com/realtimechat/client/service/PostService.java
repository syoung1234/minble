package com.realtimechat.client.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.realtimechat.client.domain.Comment;
import com.realtimechat.client.domain.Favorite;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Post;
import com.realtimechat.client.domain.PostFile;
import com.realtimechat.client.dto.request.PostRequestDto;
import com.realtimechat.client.dto.response.*;
import com.realtimechat.client.repository.CommentRepository;
import com.realtimechat.client.repository.FavoriteRepository;
import com.realtimechat.client.repository.FollowRepository;
import com.realtimechat.client.repository.MemberRepository;
import com.realtimechat.client.repository.PostFileRepository;
import com.realtimechat.client.repository.PostRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private final PostFileRepository postFileRepository;
    private final PostFileService postFileService;
    private final S3Upload s3Upload;

    /**
     * @param member
     * @param nickname
     * @param pageable
     * @return Page<PostListResponseDto>
     */
    public Page<PostResponseDto> list(Member member, String nickname, Pageable pageable) {
        return postRepository.findAllByPostAndFollowingAndCountCommentAndCountFavorite(member, nickname, pageable);
    }

    // get
    public PostResponseDto find(Member member, Integer id, Pageable pageable) {
        // Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        return postRepository.findByPostAndCountCommentAndCountFavorite(id);
    }

    // create
    @Transactional
    public String save(PostRequestDto requestDto) {
        Post post = postRepository.save(requestDto.toEntity());
        List<MultipartFile> files = requestDto.getFiles();

        if (files != null) {
            postFileService.uploadSave(files, post);
        }

        return "success";
    }
    
    // update
    @Transactional
    public String update(Integer id, PostRequestDto requestDto) {
        String message = "fail";
        List<MultipartFile> files = requestDto.getFiles();
        List<String> deleteList = requestDto.getDeleteList();
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));


        // 작성자만 수정 가능 
        if (postRepository.findById(id).get().getMember().getId().equals(requestDto.getMember().getId())) {
            // 이미지 업로드
            if (files != null) {
                postFileService.uploadSave(files, post);
            }
            // 이미지 삭제
            if (deleteList != null) {
                postFileService.delete(deleteList);
            }
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
            List<PostFile> postFileList = postFileRepository.findByPost(post);
            // 이미지 삭제
            if (postFileList != null) {
                for (PostFile postFile : postFileList) {
                    s3Upload.delete(postFile.getFilePath());
                }
            }
            // 게시글 삭제 
            postRepository.delete(post);
            message = "success";
        }
        return message;
        
    }
    

}
