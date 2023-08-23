package com.minble.client.domain;

import java.util.UUID;

// import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

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
