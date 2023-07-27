package com.realtimechat.client.repository;

import com.realtimechat.client.domain.ChatRoom;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Role;
import com.realtimechat.client.dto.request.MessageRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private ChatRoomRepository chatRoomRepository;
//
//    @Autowired
//    private MessageRepository messageRepository;

    @Test
    @Transactional
    void 멤버등록() {
        // given
        final Member member = Member.builder()
                .email("1test@test.com")
                .password(passwordEncoder.encode("1234"))
                .nickname("1test")
                .profilePath("/files/20221109_dbee2336-780d-49ca-9433-a72fa26b878e.png")
                .role(Role.ROLE_MEMBER)
                .social(null)
                .emailConfirmation(false)
                .build();

        // when
        Member result = memberRepository.save(member);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getEmail()).isEqualTo("1test@test.com");
        assertThat(result.getRole()).isEqualTo(Role.ROLE_MEMBER);
    }


    @Test
    @Transactional
    void 멤버list등록() {
        // given
        for (int i = 1; i <= 20; i++) {
            Member member = Member.builder()
                    .email("testtest" + i + "@test.com")
                    .password(passwordEncoder.encode("1234"))
                    .nickname("testtest" + i)
                    .profilePath("/files/20221109_dbee2336-780d-49ca-9433-a72fa26b878e.png")
                    .role(Role.ROLE_MEMBER)
                    .social(null)
                    .emailConfirmation(true)
                    .build();

            memberRepository.save(member);
        }

        /* RoleType 변경은 AdminMemberService 에서 진행 */
        for (int i = 100; i <= 110; i++) {
            Member member = Member.builder()
                    .email("star" + i + "@test.com")
                    .password(passwordEncoder.encode("1234"))
                    .nickname("star" + i)
                    .profilePath("/files/20221109_dbee2336-780d-49ca-9433-a72fa26b878e.png")
                    .role(Role.ROLE_STAR)
                    .social(null)
                    .emailConfirmation(true)
                    .build();
            memberRepository.save(member);


            /* Role Type 변경 시 아래 코드 추가 */
//            ChatRoom chatRoom = ChatRoom.builder().member(member).channel(member.getNickname()).build();
//            chatRoomRepository.save(chatRoom);
//
//            String content = "안녕하세요. " + member.getNickname() + " 구독자만 사용 가능한 공간입니다.";
//            MessageRequestDto messageRequestDto = new MessageRequestDto(member, chatRoom, content);
//            messageRepository.save(messageRequestDto.toEntity());
        }

        // when
        List<Member> result = memberRepository.findAll();

        // then
        assertThat(result.size()).isGreaterThan(20);
    }


}