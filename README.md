[![Java CI with Gradle](https://github.com/syoung1234/minble/actions/workflows/gradle.yml/badge.svg)](https://github.com/syoung1234/minble/actions/workflows/gradle.yml)

## 프로젝트 소개
- 1인 프로젝트입니다. 
- Spring Boot, JPA(QueryDSL), MySQL, Redis 로 만든 SNS 형식의 팬 카페입니다.
- 로그인은 JWT 구현하였습니다.
- Refresh Token을 Redis에 저장합니다.
- 소셜로그인을 구현하였습니다. (네이버, 카카오, 구글)
- WebSocket으로 실시간 채팅을 구현하였습니다.
- 파일을 AWS S3에 저장합니다.
- AWS EC2, Route53, Load Balancer, Docker를 통해 배포하였습니다.

### 배포 과정
![github-actions-front-back drawio](https://github.com/syoung1234/minble/assets/71418436/4bcfd8dd-5c9c-4b82-8b6a-eeab861c9bd8)


### 배포 주소
https://sy-minble.com

### Front-end GitHub
https://github.com/syoung1234/minble-vuejs

### 개발 기간
2022.11 - 2023.03 <br>
2023.07 - 현재 (리팩토링 진행중)
<br>

### 리팩토링 현황
1. 일부 파라미터 Map 구조 -> DTO 구조로 변경
2. [QueryDSL 추가](https://velog.io/@sysy123/Spring-Boot-%EA%B0%9C%EC%9D%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85-N1%EC%9D%84-%ED%95%B4%EA%B2%B0%ED%95%B4%EB%B3%B4%EC%9E%90-%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81)
   - 동적 쿼리 JPQL로 하였으나 리팩토링 과정에서 N+1 문제를 해결하기 위해 쿼리를 변경함
3. [RefreshToken 저장 방법 변경](https://velog.io/@sysy123/%EA%B0%9C%EC%9D%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-Refresh-Token-%EC%9D%84-Redis-%EC%97%90-%EC%A0%80%EC%9E%A5%ED%95%B4%EB%B3%B4%EC%9E%90)
   - 프로젝트에 Redis 추가
   - MySQL 저장에서 Redis 저장으로 변경

## 미리보기
<img src="https://github.com/syoung1234/minble/assets/71418436/e2f94bcd-399e-4bf4-9932-5bfbd70245d9" style="width: 300px; height: 600px">
<img src="https://github.com/syoung1234/minble/assets/71418436/ba07f826-b4c6-4295-9f45-afc768c625ca" style="width: 300px; height: 600px">
<br>
메시지 기능과 게시글 쓰기, 댓글 쓰기 


## 주요 기능
### 로그인 기능 JWT
#### [JwtTokenProvider.java](src/main/java/com/minble/client/config/security/JwtTokenProvider.java)
Access Token, Refresh Token 생성
``` java
    // 토큰 유효시간 30분
    private long tokenValidTime = 3 * 60 * 10000L;
    
    // 객체 초기화, secretKey를 Base64로 인코딩
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(String userPK, Role roles, String social) {
        Claims claims = Jwts.claims().setSubject(userPK); // JWT payload에 저장되는 정보 단위
        claims.put("roles", roles); // 정보 저장 (key-value)
        claims.put("social", social);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 사용할 암호화 알고리즘과 signature에 들어갈 secret 값 세팅
                .compact();
    } 

    // Refresh Token 생성
    public String createRefreshToken(Member member) {
        String refreshTokenId = UUID.randomUUID().toString();
        LocalDateTime expirationDate = LocalDateTime.now().plusMonths(1);
        long expirationTime = Timestamp.valueOf(expirationDate).getTime();

        RefreshToken refreshToken = new RefreshToken(refreshTokenId, member, expirationTime);
        refreshTokenRepository.save(refreshToken);

        return refreshTokenId;
    }

```

#### 로그인
[MemberController.java](src/main/java/com/minble/client/controller/MemberController.java)
```java
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MemberController {
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto response = memberService.login(loginRequestDto);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", response.getRefreshToken())
                .maxAge(30 * 24 * 60 * 60) // 30일
                .httpOnly(true)
                .secure(true)
                .path("/")
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(response);
    }
}
```

#### Refresh Token을 Redis에 저장
build.gradle
```
dependencies {
        implementation 'org.springframework.boot:spring-boot-starter-data-redis'
}
```
[RefreshToken.java](src/main/java/com/minble/client/domain/RefreshToken.java)
```java
@Getter
@NoArgsConstructor
@RedisHash(value = "refresh_token")
public class RefreshToken {
    @Id
    private String id;
    private UUID memberId;
    private String email;
    private String roleType;
    private String social;
    @TimeToLive
    private long expirationTime;

    @Builder
    public RefreshToken(String id, Member member, long expirationTime) {
        this.id = id;
        this.memberId = member.getId();
        this.email = member.getEmail();
        this.roleType = member.getRole().toString();
        this.social = member.getSocial();
        this.expirationTime = expirationTime;
    }
}
```
Refresh Token 만들고 httpOnly, secure를 사용하여 cookie에 저장하였습니다. Refresh Token은 30일 저장, Access Token은 30분 저장하였습니다. Access Token이 만료되었을 때는 Refresh Token을 요청하여 새로 Access Token을 받아서 로그인이 풀리지 않도록 하였습니다.
<br>

### 실시간 채팅 WebSocket

[WebSocketConfig.java](src/main/java/com/minble/client/config/WebSocketConfig.java)
<br>
[MessageController.java](src/main/java/com/minble/client/controller/MessageController.java)
<br>


### AWS S3 파일 저장

[S3Config.java](src/main/java/com/minble/client/config/S3Config.java)
<br>

### Social Login

[CustomOAuth2UserService.java](src/main/java/com/minble/client/config/oauth/CustomOAuth2UserService.java)
<br>
[OAuth2Attributes.java](src/main/java/com/minble/client/config/oauth/OAuth2Attributes.java)
<br>
[OAuth2SuccessHandler.java](src/main/java/com/minble/client/config/oauth/OAuth2SuccessHandler.java)
<br>


## Version
Java: 17.0.2
<br>
Spring Boot: 2.7.5
<br>
MySQL: 8.0.31
<br>
