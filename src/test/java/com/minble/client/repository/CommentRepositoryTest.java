package com.minble.client.repository;

import com.minble.client.domain.Comment;
import com.minble.client.domain.Member;
import com.minble.client.domain.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

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