# minble
1인 프로젝트 <br>
SNS 형식의 팬 카페입니다. 로그인은 JWT 구현하였습니다. 게시판에 글을 쓰고 댓글과 답글 달기 뿐만 아니라 실시간 채팅이 가능합니다. 실시간 채팅은 1:N 관계로 Role 타입에 따라 보여지는 메시지가 다릅니다. Role 타입은 Star와 User로 구분이 되며 Star 타입은 N명에게 메시지를 보내고 볼 수 있으며 User 타입은 Star 1명에게 메시지를 보내고 볼 수 있습니다. Message 기능은 Store 에서 결제를 통해 이용할 수 있으며 테스트 계정은 결제 없이 바로 사용할 수 있습니다.<br> 

또한 AWS EC2, Route53, Load Balancer 를 통해 배포하였습니다. 

Web Site: https://sy-minble.com
<br><br>

## Message 기능 (실시간 채팅) 미리보기
<img src="https://github.com/syoung1234/minble/assets/71418436/e2f94bcd-399e-4bf4-9932-5bfbd70245d9" style="width: 300px; height: 600px">

## 게시글 좋아요, 댓글 쓰기 미리보기
<img src="https://github.com/syoung1234/minble/assets/71418436/ba07f826-b4c6-4295-9f45-afc768c625ca" style="width: 300px; height: 600px">

## 주요 기능
### JWT

[JwtAuthenticationFilter.java](src/main/java/com/realtimechat/client/config/security/JwtAuthenticationFilter.java)
<br>
[JwtTokenProvider.java](src/main/java/com/realtimechat/client/config/security/JwtTokenProvider.java)
<br>
[SecurityConfig.java](src/main/java/com/realtimechat/client/config/security/SecurityConfig.java)
<br>
[SecurityUser.java](src/main/java/com/realtimechat/client/config/security/SecurityUser.java)
<br>
[SecurityUserDetailService.java](src/main/java/com/realtimechat/client/config/security/SecurityUserDetailService.java)
<br>
---

### WebSocket

[WebSocketConfig.java](src/main/java/com/realtimechat/client/config/WebSocketConfig.java)
<br>
[MessageController.java](src/main/java/com/realtimechat/client/controller/MessageController.java)
<br>
---

### AWS S3

[S3Config.java](src/main/java/com/realtimechat/client/config/S3Config.java)
<br>
---
### Social Login

[CustomOAuth2UserService.java](src/main/java/com/realtimechat/client/config/oauth/CustomOAuth2UserService.java)
<br>
[OAuth2Attributes.java](src/main/java/com/realtimechat/client/config/oauth/OAuth2Attributes.java)
<br>
[OAuth2SuccessHandler.java](src/main/java/com/realtimechat/client/config/oauth/OAuth2SuccessHandler.java)
<br>
---

## Version
Java: 17.0.2
<br>
Spring Boot: 2.7.5
<br>
MySQL: 8.0.31
<br>