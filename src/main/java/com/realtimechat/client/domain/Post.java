package com.realtimechat.client.domain;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
@ToString(exclude = "postFileList")
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

    @OneToMany(mappedBy = "post")
    @Builder.Default
    private List<PostFile> postFileList = new ArrayList<>();

    public void addPostFile(PostFile file){
        this.postFileList.add(file);
    }

    public void update(String content) {
        this.content = content;
    }

    
}
