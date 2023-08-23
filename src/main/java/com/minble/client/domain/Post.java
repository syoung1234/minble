package com.minble.client.domain;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="member_id", nullable = false, updatable = false)
    @ToString.Exclude
    private Member member;
    
    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<PostFile> postFileList = new ArrayList<>();

    public void addPostFile(PostFile file){
        this.postFileList.add(file);
    }

    public void update(String content) {
        this.content = content;
    }

    @Builder
    public Post(Member member, String content) {
        this.member = member;
        this.content = content;
    }

    
}
