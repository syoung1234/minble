package com.realtimechat.client.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @Column(columnDefinition = "TEXT")
    private String content;
    
}
