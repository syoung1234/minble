package com.realtimechat.client.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="member_id", nullable = false, updatable = false)
    private Member member;
    
    @Column(columnDefinition = "TEXT")
    private String content;

    public void update(String content) {
        this.content = content;
    }

    
}
