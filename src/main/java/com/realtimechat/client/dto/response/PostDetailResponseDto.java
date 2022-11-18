package com.realtimechat.client.dto.response;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.realtimechat.client.domain.Comment;
import com.realtimechat.client.domain.Post;
import com.realtimechat.client.domain.PostFile;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostDetailResponseDto {
    private Integer id;
    private String content;
    private String nickname;
    private String profilePath;
    private String createdAt;
    private List<PostAndFile> postFileList;
    private List<PostAndComment> commentList;

    public PostDetailResponseDto(Post entity) {
        this.id = entity.getId();
        this.nickname = entity.getMember().getNickname();
        this.profilePath = entity.getMember().getProfilePath();
        this.content = entity.getContent();
        this.createdAt = entity.getCreated_at().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.postFileList = PostAndFile.postFileList(entity.getPostFileList());
        this.commentList = PostAndComment.commentList(entity.getCommentList());
    }

    @Getter
    static class PostAndFile {
        private String filename;
        private String filePath;

        static List<PostAndFile> postFileList(List<PostFile> files) {
            List<PostAndFile> list = new ArrayList<>();
            files.forEach(file -> {
                list.add(new PostAndFile(file));
            });

            return list;
        }

        public PostAndFile(PostFile postFile) {
            this.filename = postFile.getFilename();
            this.filePath = postFile.getFilePath();
        }
    }

    @Getter
    static class PostAndComment {
        private String nickname;
        private String content;
        private String createdAt;

        static List<PostAndComment> commentList(List<Comment> comments) {
            List<PostAndComment> commentList = new ArrayList<>();
            comments.forEach(comment -> {
                commentList.add(new PostAndComment(comment));
            });

            return commentList;
        }

        public PostAndComment(Comment comment) {
            this.nickname = comment.getMember().getNickname();
            this.content = comment.getContent();
            this.createdAt = comment.getCreated_at().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        }
    }
    

}
