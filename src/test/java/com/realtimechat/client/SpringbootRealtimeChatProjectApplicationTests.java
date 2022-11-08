package com.realtimechat.client;

import com.realtimechat.client.domain.Follow;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Post;
import com.realtimechat.client.domain.Role;
import com.realtimechat.client.dto.request.FollowRequestDto;
import com.realtimechat.client.dto.request.PostRequestDto;
import com.realtimechat.client.repository.FollowRepository;
import com.realtimechat.client.repository.MemberRepository;
import com.realtimechat.client.repository.PostRepository;
import com.realtimechat.client.service.FollowService;
import com.realtimechat.client.service.PostService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootTest
class SpringbootRealtimeChatProjectApplicationTests {
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private FollowRepository followRepository;
	@Autowired
	private PostRepository postRepository;
	
	
	// @Test
	// void contextLoads() {

	// 	// 회원가입
	// 	Role role = Role.ROLE_MEMBER;
	// 	for (int i = 1; i <= 10; i++) {
	// 		if (i < 5) {
	// 			role = Role.ROLE_STAR;
	// 		} else {
	// 			role = Role.ROLE_MEMBER;
	// 		}
	// 		memberRepository.save(Member.builder()
    //             .email("test"+i+"@test.com")
    //             .password(passwordEncoder.encode("1234"))
    //             .nickname("test"+i)
    //             .phone("01012341234")
    //             .role(role)
    //             .build()).getId();
	// 	}

	// };

	// @Test
	// void insert() {
	// 	// 팔로우 
	// 	for (int i = 1; i < 5; i ++) {
	// 		Member following = memberRepository.findByNickname("test"+i);
	// 		for (int j = 5; j < 11; j++) {
	// 			Member member = memberRepository.findByNickname("test"+j);
	// 			followRepository.save(Follow.builder()
    //             .member(member)
    //             .following(following)
    //             .build());

	// 		}
        	
	// 	}

	// 	// 게시글 작성
	// 	for (int i = 1; i < 5; i++) {
	// 		Member member = memberRepository.findByNickname("test"+i);
	// 		for (int j = 0; j < 10; j++) {
	// 			postRepository.save(Post.builder()
    //             .content("테스트 내용입니다."+i)
    //             .member(member)
    //             .build());
	// 		}
	// 	}
	// }

}
