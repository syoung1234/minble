package com.realtimechat.client.repository;

import com.realtimechat.client.domain.Comment;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest extends TestBase {

    @Autowired
    private CommentRepository commentRepository;

    @DisplayName("댓글 저장")
    @Test
    void save() {
        // given
        Member member = new Member();
        member.setId(UUID.randomUUID());

        Post post = new Post();
        post.setMember(member);

        Comment comment = Comment.builder()
                .member(member)
                .post(post)
                .content("댓글 테스트")
                .build();

        // when
        Comment result = commentRepository.save(comment);

        // then
        Assertions.assertThat(result.getPost()).isEqualTo(post);

    }
}