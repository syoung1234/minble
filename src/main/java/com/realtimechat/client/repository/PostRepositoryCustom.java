package com.realtimechat.client.repository;

import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Post;
import com.realtimechat.client.dto.response.PostListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {

    // 팔로잉한 스타 게시글 보기
    Page<PostListResponseDto> findByPostAndFollowingAndFavoriteCountAndCommentCount(Member member, String nickname, Pageable pageable);

}
