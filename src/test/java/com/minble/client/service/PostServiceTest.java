package com.minble.client.service;

import com.minble.client.domain.*;
import com.minble.client.dto.request.PostRequestDto;
import com.minble.client.dto.response.PostResponseDto;
import com.minble.client.exception.ErrorCode;
import com.minble.client.exception.MemberException;
import com.minble.client.exception.PostException;
import com.minble.client.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;
    @Mock
    private PostRepository postRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PostFileRepository postFileRepository;
    @Mock
    private FavoriteRepository favoriteRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Value("${default.profile.path}")
    private String defaultProfilePath;

    private String nickname = "star1";

//    @DisplayName("팔로우한 게시글 리스트")
//    @Test
//    void list() {
//        // given
//        Member member = memberRepository.save(member(10));
//        Pageable pageable = PageRequest.of(0, 10);
//        List<PostResponseDto> list = Arrays.asList(
//                new PostResponseDto("star1", "profilePath", 10,"게시글 내용!", LocalDateTime.now(),3, 4),
//                new PostResponseDto(),
//                new PostResponseDto()
//        );
//        Page<PostResponseDto> pages = new PageImpl<>(list, pageable, list.size());
//        doReturn(pages).when(postRepository).findAllByPostAndFollowingAndCountCommentAndCountFavorite(member, nickname, pageable);
//
//        // when
//        Page<PostResponseDto> result = postService.list(member, nickname, pageable);
//
//        // then
//        assertThat(result.get()).isNotNull();
//        assertThat(result.get().count()).isEqualTo(3);
//        assertThat(result.get().findFirst().get().getCommentCount()).isEqualTo(3);
//    }

    @DisplayName("게시글 상세 조회")
    @Test
    void find() {
        // given
        Member member = new Member();
        List<PostFile> postFileList = new ArrayList<>();
        Favorite favorite = new Favorite();
        PostResponseDto postResponseDto = new PostResponseDto("star1", "profilePath", 10,"게시글 내용!", LocalDateTime.now(),20, 4);
        doReturn(postResponseDto).when(postRepository).findByPostAndCountCommentAndCountFavorite(10);
        when(postFileRepository.findAllByPostId(any())).thenReturn(postFileList);
        when(favoriteRepository.findByMemberAndPostId(any(Member.class), anyInt())).thenReturn(Optional.of(favorite));

        // when
        PostResponseDto result = postService.find(10, member);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getCommentCount()).isEqualTo(20);
    }

    @DisplayName("조회한 게시글이 없을 때")
    @Test
    void not_found_post() {
        // given
        Member member = new Member();
        doReturn(null).when(postRepository).findByPostAndCountCommentAndCountFavorite(10);

        // when
        PostException postException = assertThrows(PostException.class, () -> postService.find(10, member));

        // then
        assertThat(postException.getErrorCode()).isEqualTo(ErrorCode.POST_NOT_FOUND);
    }

    @DisplayName("게시글 생성 실패-멤버 없음")
    @Test
    void not_found_member() {
        // given
        Member member = null;

        // when
        MemberException memberException = assertThrows(MemberException.class,
                () -> postService.save(new PostRequestDto(), member));

        // then
        assertThat(memberException.getErrorCode()).isEqualTo(ErrorCode.MEMBER_NOT_FOUND);
    }

    @DisplayName("글쓰기 권한 없음")
    @Test
    void unauthorized_role() {
        // given
        Member member = new Member();
        member.setRole(Role.ROLE_MEMBER);

        // when
        MemberException memberException = assertThrows(MemberException.class,
                () -> postService.save(new PostRequestDto(), member));

        // then
        assertThat(memberException.getErrorCode()).isEqualTo(ErrorCode.FORBIDDEN_MEMBER);
    }

    @DisplayName("게시글 생성 성공")
    @Test
    void save() {
        // given
        PostRequestDto requestDto = new PostRequestDto();
        Member member = new Member();
        Post post = post(member);
        requestDto.setMember(member);
        post.setId(1);
        member.setRole(Role.ROLE_STAR);

        doReturn(post).when(postRepository).save(any(Post.class));

        // when
        int postId = postService.save(requestDto, member);

        // then
        assertThat(postId).isEqualTo(post.getId());
    }


    private Member member(int i) {
        return Member.builder()
                .email("test" + i + "@test.com")
                .password(passwordEncoder.encode("1234"))
                .nickname("test" + i)
                .profilePath(defaultProfilePath)
                .role(Role.ROLE_MEMBER)
                .social(null)
                .emailConfirmation(true)
                .build();
    }

    private Post post(Member member) {
        return Post.builder()
                .member(member)
                .content("테스트")
                .build();
    }

}