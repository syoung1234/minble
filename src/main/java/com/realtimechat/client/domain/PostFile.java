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
public class PostFile extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "post_file_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(length = 100)
    private String name;

    @Column(name = "original_name", length = 100)
    private String originalName;

    private String path;

    private Integer size;

    @Column(length = 10)
    private String type;
    
}
