package com.realtimechat.client.repository;

import com.realtimechat.client.domain.Comment;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentRepositoryTest extends TestBase {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;


    @DisplayName("댓글 저장")
    @Transactional
    @Test
    void save() {
        // given
        Member member = memberRepository.save(createStarMember(10));
        Post post = postRepository.save(createPost(member));

        // when
        Comment comment = commentRepository.save(createComment(member, post));

        // then
        Assertions.assertThat(comment.getPost()).isEqualTo(post);

    }
}