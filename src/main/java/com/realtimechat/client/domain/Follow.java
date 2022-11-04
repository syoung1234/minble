package com.realtimechat.client.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@Entity
public class Follow extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "follower_member_id", nullable = false, updatable = false)
    private Member follower;

    @ManyToOne
    @JoinColumn(name = "following_member_id", nullable = false, updatable = false)
    private Member following;
    
}
