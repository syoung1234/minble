package com.realtimechat.client.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
// import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    private Member member;
    // @TimeToLive
    private LocalDateTime expirationDate;

    @Builder
    public RefreshToken(String id, Member member, LocalDateTime expirationDate) {
        this.id = id;
        this.member = member;
        this.expirationDate = expirationDate; 
    }
}
