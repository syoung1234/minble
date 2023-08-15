package com.realtimechat.client.repository;

import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest extends TestBase {

    @Autowired
    private PostRepository postRepository;

    @Test
    void 게시글_등록() {
        // given
        Member member = new Member();
        member.setId(UUID.randomUUID());

        Post post = new Post();
        post.setContent("test");
        post.setMember(member);

        // when
        Post result = postRepository.save(post);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo("test");
    }

    @DisplayName("본인 게시글 count")
    @Test
    void list() {
        // given
        Member member = new Member();
        member.setId(UUID.randomUUID());

        for (int i = 0; i < 3; i++) {
            Post post = new Post();
            post.setContent("test");
            post.setMember(member);

            postRepository.save(post);
        }

        // when
        List<Post> postList = postRepository.findByMember(member);

        // then
        assertThat(postList.size()).isEqualTo(3);
    }

}