package com.realtimechat.client.repository;

import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Post;
import com.realtimechat.client.domain.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${default.profile.path}")
    private String defaultProfilePath;

    @Test
    // @Transactional
    void 게시글_등록() {
        // given
        Member member = memberRepository.save(starMember(1));

        Post post = Post.builder()
                .member(member)
                .content("test")
                .build();

        // when
        Post result = postRepository.save(post);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo("test");
    }

    @DisplayName("본인 게시글 count")
    @Test
    @Transactional
    void list() {
        // given
        Member member = memberRepository.save(starMember(10));

        Post post1 = Post.builder()
                .member(member)
                .content("test1")
                .build();

        Post post2 = Post.builder()
                .member(member)
                .content("test2")
                .build();

        // when
        postRepository.save(post1);
        postRepository.save(post2);
        List<Post> postList = postRepository.findByMember(member);

        // then
        assertThat(postList.size()).isEqualTo(2);
    }


    private Member starMember(int i) {
        return Member.builder()
                .email("star" + i + "@test.com")
                .password(passwordEncoder.encode("1234"))
                .nickname("star" + i)
                .profilePath(defaultProfilePath)
                .role(Role.ROLE_STAR)
                .social(null)
                .emailConfirmation(true)
                .build();
    }

}