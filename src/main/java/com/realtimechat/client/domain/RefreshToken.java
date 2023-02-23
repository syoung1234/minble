package com.realtimechat.client.domain;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class RefreshToken extends BaseTimeEntity {
    @Id
    @Column(name = "refresh_token_id")
    private String id;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Builder
    public RefreshToken(String id, Member member, LocalDateTime expirationDate) {
        this.id = id;
        this.member = member;
        this.expirationDate = expirationDate; 
    }
}
