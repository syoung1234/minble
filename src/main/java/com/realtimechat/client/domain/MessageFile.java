package com.realtimechat.client.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class MessageFile extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "image_file_id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "message_id")
    private Message message;

    @Column(name = "file_name", length = 100)
    private String filename;

    @Column(name = "original_file_name", length = 100)
    private String originalFileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "file_type", length = 10)
    private String fileType;

    @Builder
    public MessageFile(Message message, String filename, String originalFileName, String filePath, Long fileSize, String fileType) {
        this.message = message;
        this.filename = filename;
        this.originalFileName = originalFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.fileType = fileType;
    }
}
