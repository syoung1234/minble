package com.realtimechat.client.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PostFile extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "post_file_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "file_name", length = 100)
    private String filename;

    @Column(name = "original_file_name", length = 100)
    private String originalFileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(length = 10)
    private String fileType;

    @Builder
    public PostFile(Post post, String filename, String originalFileName, String filePath, Long fileSize, String fileType) {
        this.post = post;
        this.filename = filename;
        this.originalFileName = originalFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.fileType = fileType;
    }
    
}
