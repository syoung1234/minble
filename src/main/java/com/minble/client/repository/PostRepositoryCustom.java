package com.minble.client.repository;

import com.minble.client.domain.Member;
import com.minble.client.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    // 팔로잉한 스타 게시글 보기
    Page<PostResponseDto> findAllByPostAndFollowingAndCountCommentAndCountFavorite(Member member, String nickname, Pageable pageable);

    // 게시글 상세 조회
    PostResponseDto findByPostAndCountCommentAndCountFavorite(int postId);

}
