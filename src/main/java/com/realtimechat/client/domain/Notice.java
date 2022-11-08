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
public class Notice extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "notice_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 100)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String content;
    
}
