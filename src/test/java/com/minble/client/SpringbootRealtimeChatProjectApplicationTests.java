package com.minble.client;

import com.minble.client.repository.ChatRoomRepository;
import com.minble.client.repository.FollowRepository;
import com.minble.client.repository.MemberRepository;
import com.minble.client.repository.MessageRepository;
import com.minble.client.repository.PostRepository;
import com.minble.client.repository.SubscriberRepository;

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
	@Autowired
	private ChatRoomRepository chatRoomRepository;
	@Autowired
	private SubscriberRepository subscriberRepository;
	@Autowired
	private MessageRepository messageRepository;

	
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

	// @Test
	// void insertChatRomm() {
	// 	// 채팅방 데이터 추가
	// 	List<Member> memberList = memberRepository.findByRole(Role.ROLE_STAR);

	// 	for (Member member : memberList) {
	// 		ChatRoom chatRoom = new ChatRoom();
	// 		chatRoom.setMember(member);
	// 		chatRoom.setChannel(member.getNickname());
	// 		chatRoomRepository.save(chatRoom);
	// 	}
	// }

	// @Test
	// void insertSubscriber() {
	// 	// 구독자 데이터 생성
	// 	List<Member> memberList = memberRepository.findByRole(Role.ROLE_MEMBER);
	// 	Integer count = 0;
	// 	Optional<ChatRoom> chatRoom = chatRoomRepository.findById(235);
	// 	for (Member member : memberList) {
	// 		count++;
	// 		if(count > 5) {
	// 			break;
	// 		}

	// 		Subscriber subscriber = new Subscriber();
	// 		subscriber.setMember(member);
	// 		subscriber.setChatRoom(chatRoom.orElse(null));
	// 		subscriber.setExpiredAt(LocalDateTime.now().plusMonths(1));
	// 		member.setRole(Role.ROLE_SUBSCRIBER);
	// 		memberRepository.save(member);
	// 		subscriberRepository.save(subscriber);
	// 	}
	// }

	// @Test
	// void insertMessage() {
	// 	List<ChatRoom> chatRoomList = chatRoomRepository.findAll();
	// 	for (ChatRoom chatRoom : chatRoomList) {
	// 		Message message = new Message();
	// 		message.setChatRoom(chatRoom);
	// 		message.setContent("안녕하세요. "+ chatRoom.getMember().getNickname() + " 구독자만 사용 가능한 공간입니다.");
	// 		message.setMember(chatRoom.getMember());

	// 		messageRepository.save(message);
	// 	}
	// }


}
