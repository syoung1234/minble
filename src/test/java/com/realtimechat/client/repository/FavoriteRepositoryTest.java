package com.realtimechat.client.repository;

import com.realtimechat.client.domain.Favorite;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FavoriteRepositoryTest extends TestBase {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Test
    @Transactional
    void save() {
        // given
        Member member = createMember(10);
        Post post = createPost(member);
        createPost(member);
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