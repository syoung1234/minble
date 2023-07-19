package com.realtimechat.client.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.realtimechat.client.domain.*;
import com.realtimechat.client.dto.response.PostListResponseDto;
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

    @Override
    public Page<PostListResponseDto> findByPostAndFollowingAndFavoriteCountAndCommentCount(Member member, String nickname, Pageable pageable) {
        QPost qPost = QPost.post;
        QFollow qFollow = QFollow.follow;
        QComment subComment = new QComment("subComment");
        QFavorite subFavorite = new QFavorite("subFavorite");
        QMember qMember = QMember.member;
        BooleanBuilder builder = new BooleanBuilder();

        JPAQuery<PostListResponseDto> list = queryFactory
                .select(Projections.constructor(
                        PostListResponseDto.class,
                        qPost.member.nickname,
                        qPost.member.profilePath,
                        qPost.content,
                        JPAExpressions.select(subComment.count())
                                .from(subComment)
                                .where(subComment.post.eq(qPost)),
                        JPAExpressions.select(subFavorite.count())
                                .from(subFavorite)
                                .where(subFavorite.post.eq(qPost))
                ))
                .from(qPost);

        if (nickname == null || nickname.equals("")) { // 팔로우한 모든 멤버 조회
            builder.and(qPost.member.eq(qFollow.following));

            list.join(qFollow).on(qFollow.member.eq(member));
        } else { // 선택한 멤버 조회
            builder.and(qPost.member.eq(qMember));

            list.join(qMember).on(qMember.nickname.eq(nickname));
        }

        list.where(builder).orderBy(qPost.createdAt.desc());

        List<PostListResponseDto> content = list
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = list.fetch().size();

        return new PageImpl<>(content, pageable, total);
    }

}
