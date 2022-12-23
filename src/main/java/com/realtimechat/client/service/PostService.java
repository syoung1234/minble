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
import com.realtimechat.client.dto.request.PostRequestDto;
import com.realtimechat.client.dto.response.CommentResponseDto;
import com.realtimechat.client.dto.response.FollowResponseDto;
import com.realtimechat.client.dto.response.PostDetailResponseDto;
import com.realtimechat.client.dto.response.PostResponseDto;
import com.realtimechat.client.repository.CommentRepository;
import com.realtimechat.client.repository.FavoriteRepository;
import com.realtimechat.client.repository.FollowRepository;
import com.realtimechat.client.repository.MemberRepository;
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
    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final FavoriteRepository favoriteRepository;
    private final PostFileService postFileService;

    // list
    public Map<String, Object> list(Member member, String nickname, Pageable pageable) {
        List<FollowResponseDto> follow = followRepository.findByMember(member);
        List<Map<String,Object>> listmap = new ArrayList<Map<String, Object>>();
        List<Member> memberList = new ArrayList<>();
        Map<String, Object> result = new HashMap<String, Object>();
        Page<Post> posts;

        // role이 star일 경우 자기 자신 게시글 보여주고 글 작성 권한이 있음
        if (member.getRole().toString().equals("ROLE_STAR")) {
            result.put("role", "star");
            nickname = member.getNickname();
        }

        if (nickname == null || nickname == "") {
            /*************** 팔로잉 목록 ***************/
            // 팔로잉한 member 찾은 후 nickname, profile만 보여줌 
            for (FollowResponseDto following : follow) {
                Map<String, Object> followingList = new HashMap<String, Object>();

                followingList.put("nickname", following.getFollowing().getNickname());
                followingList.put("profilePath", following.getFollowing().getProfilePath());
                listmap.add(followingList);
                memberList.add(following.getFollowing());
            }
            /*************** 팔로잉한 게시글 목록 ***************/
            posts = postRepository.findByMemberOrderByCreatedAtDesc(memberList, pageable);
        } else {
            /*************** 특정 회원 게시글 목록 ***************/
            Member following = memberRepository.findByNickname(nickname);
            posts = postRepository.findByMemberOrderByCreatedAtDesc(following, pageable);
        }
        
        result.put("following", listmap);
        
        List<PostResponseDto> postList = new ArrayList<>();

        for (Post post : posts) {
            PostResponseDto postResponseDto = new PostResponseDto(post);
            postResponseDto.setFavoriteCount(favoriteRepository.countByPost(post)); // 좋아요 수 
            postResponseDto.setCommentCount(commentRepository.countByPost(post)); // 댓글 수
            // 좋아요 여부
            Favorite favorite = favoriteRepository.findByMemberAndPost(member, post);
            if (favorite == null) {
                postResponseDto.setFavorite(false);
            } else {
                postResponseDto.setFavorite(true);
            }
            postList.add(postResponseDto);
        }

        result.put("postList", postList);

        // page
        Map<String, Integer> pageList = new HashMap<>();
        pageList.put("page", posts.getNumber());
        pageList.put("totalPages", posts.getTotalPages());
        pageList.put("nextPage", pageable.next().getPageNumber());
        result.put("pageList", pageList);

        return result;
    }

    // get
    public PostDetailResponseDto find(Member member, Integer id, Pageable pageable) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        Page<Comment> comments = commentRepository.findByPostAndDepth(post, 0, pageable);

        PostDetailResponseDto postDetailResponseDto = new PostDetailResponseDto(post);
        // 댓글 목록
        List<CommentResponseDto> commentList = comments.stream().map(CommentResponseDto::new).collect(Collectors.toList());
        
        // 페이지 정보
        Map<String, Integer> pageList = new HashMap<>();
        pageList.put("page", comments.getNumber());
        pageList.put("totalPages", comments.getTotalPages());
        pageList.put("nextPage", pageable.next().getPageNumber());

        postDetailResponseDto.setCommentList(commentList); // 댓글 목록 
        postDetailResponseDto.setFavoriteCount(favoriteRepository.countByPost(post)); // 좋아요 수
        postDetailResponseDto.setCommentCount(commentRepository.countByPost(post)); // 댓글 수 
        postDetailResponseDto.setPageList(pageList);

         // 좋아요 여부
         Favorite favorite = favoriteRepository.findByMemberAndPost(member, post);
         if (favorite == null) {
            postDetailResponseDto.setFavorite(false);
         } else {
            postDetailResponseDto.setFavorite(true);
         }

        return postDetailResponseDto;
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
            postRepository.delete(post);
            message = "success";
        }
        return message;
        
    }
    

}
