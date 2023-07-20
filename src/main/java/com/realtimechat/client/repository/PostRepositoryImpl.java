package com.realtimechat.client.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.realtimechat.client.domain.*;
import com.realtimechat.client.dto.response.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /* 공통 쿼리: nickname, profilePath, postId, content, createdAt, subComment.count(), subFavorite.count() */
    private JPAQuery<PostResponseDto> getPostCommonQuery(QPost qPost, QComment subComment, QFavorite subFavorite) {
        return queryFactory
                .select(Projections.constructor(
                        PostResponseDto.class,
                        qPost.member.nickname,
                        qPost.member.profilePath,
                        qPost.id,
                        qPost.content,
                        qPost.createdAt,
                        JPAExpressions.select(subComment.count())
                                .from(subComment)
                                .where(subComment.post.eq(qPost)),
                        JPAExpressions.select(subFavorite.count())
                                .from(subFavorite)
                                .where(subFavorite.post.eq(qPost))
                ))
                .from(qPost);
    }

    /* 팔로잉한 스타의 모든 게시글 조회 or 스타 멤버 1명 게시글 조회 */
    @Override
    public Page<PostResponseDto> findAllByPostAndFollowingAndCountCommentAndCountFavorite(Member member, String nickname, Pageable pageable) {
        QPost qPost = QPost.post;
        QFollow qFollow = QFollow.follow;
        QComment subComment = new QComment("subComment");
        QFavorite subFavorite = new QFavorite("subFavorite");
        QMember qMember = QMember.member;
        BooleanBuilder builder = new BooleanBuilder();

        JPAQuery<PostResponseDto> list = getPostCommonQuery(qPost, subComment, subFavorite);

        if (nickname == null || nickname.equals("")) { // 팔로우한 모든 멤버 조회
            builder.and(qPost.member.eq(qFollow.following));

            list.join(qFollow).on(qFollow.member.eq(member));
        } else { // 선택한 멤버 조회
            builder.and(qPost.member.eq(qMember));

            list.join(qMember).on(qMember.nickname.eq(nickname));
        }

        list.where(builder).orderBy(qPost.createdAt.desc());

        List<PostResponseDto> content = list
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = list.fetch().size();

        return new PageImpl<>(content, pageable, total);
    }

    /* 게시글 상세 조회 */
    @Override
    public PostResponseDto findByPostAndCountCommentAndCountFavorite(int postId) {
        QPost qPost = QPost.post;
        QComment subComment = new QComment("subComment");
        QFavorite subFavorite = new QFavorite("subFavorite");

        return getPostCommonQuery(qPost, subComment, subFavorite)
                .where(qPost.id.eq(postId))
                .fetchFirst();
    }

}
