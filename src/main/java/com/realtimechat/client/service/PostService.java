package com.realtimechat.client.service;


import java.util.*;
import java.util.stream.Collectors;

import com.realtimechat.client.domain.*;
import com.realtimechat.client.dto.request.PostRequestDto;
import com.realtimechat.client.dto.response.*;
import com.realtimechat.client.exception.ErrorCode;
import com.realtimechat.client.exception.MemberException;
import com.realtimechat.client.exception.PostException;
import com.realtimechat.client.repository.FavoriteRepository;
import com.realtimechat.client.repository.PostFileRepository;
import com.realtimechat.client.repository.PostRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import static java.util.stream.Collectors.toList;

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
    private final FavoriteRepository favoriteRepository;
    private final PostFileService postFileService;
    private final S3Upload s3Upload;

    /**
     * nickname == null 팔로잉한 멤버의 전체 게시글 조회, != null 한 멤버의 게시글 조회
     * @param member 로그인한 멤버
     * @param nickname star 닉네임
     * @param pageable 페이지
     * @return Page<PostListResponseDto>
     */
    public Page<PostResponseDto> list(Member member, String nickname, Pageable pageable) {
        Page<PostResponseDto> postResponseDto = postRepository.findAllByPostAndFollowingAndCountCommentAndCountFavorite(member, nickname, pageable);
        if (postResponseDto.isEmpty()) {
            throw new PostException(ErrorCode.POST_NOT_FOUND);
        }

        postResponseDto.getContent().forEach(postDto -> {
            addPostResponseDto(postDto, member);
        });

        return postResponseDto;
    }

    /**
     * 게시글 상세 조회
     * @param id 게시글 Id
     * @return PostResponseDto
     */
    public PostResponseDto find(Integer id, Member member) {
        PostResponseDto postResponseDto = postRepository.findByPostAndCountCommentAndCountFavorite(id);
        if (postResponseDto == null) {
            throw new PostException(ErrorCode.POST_NOT_FOUND);
        }
        addPostResponseDto(postResponseDto, member);

        return postResponseDto;
    }

    /**
     * 게시글 작성
     * @param requestDto (String content, Member member, List<MultipartFile> files, List<String> deleteList)
     * @param member 작성자
     * @return void
     */
    @Transactional
    public int save(PostRequestDto requestDto, Member member) {
        if (member == null) throw new MemberException(ErrorCode.MEMBER_NOT_FOUND);
        if (!member.getRole().equals(Role.ROLE_STAR)) throw new MemberException(ErrorCode.FORBIDDEN_MEMBER);

        requestDto.setMember(member);
        Post post = postRepository.save(requestDto.toEntity());
        List<MultipartFile> files = requestDto.getFiles();

        if (files != null) {
            postFileService.uploadSave(files, post);
        }

        return post.getId();
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

    private void addPostResponseDto(PostResponseDto postResponseDto, Member member) {
        List<PostFileResponseDto> filePath = postFileRepository.findAllByPostId(postResponseDto.getPostId())
                .stream()
                .map(p -> new PostFileResponseDto(p.getFilename(), p.getFilePath()))
                .collect(Collectors.toList());
        postResponseDto.setPostFileList(filePath);

        Optional<Favorite> favorite = favoriteRepository.findByMemberAndPostId(member, postResponseDto.getPostId());
        postResponseDto.setFavorite(favorite.isPresent());
    }

}
