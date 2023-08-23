package com.minble.client.repository;

import com.minble.client.domain.Favorite;
import com.minble.client.domain.Member;
import com.minble.client.domain.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class FavoriteRepositoryTest extends TestBase {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Test
    void save() {
        // given
        Member member = new Member();
        member.setId(UUID.randomUUID());
        Post post = new Post();
        post.setMember(member);

        Favorite favorite = Favorite.builder()
                .member(member)
                .post(post)
                .build();

        // when
        Favorite result = favoriteRepository.save(favorite);

        // then
        assertThat(result).isNotNull();

    }
}