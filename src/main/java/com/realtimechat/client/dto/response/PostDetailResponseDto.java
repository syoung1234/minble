package com.realtimechat.client.dto.response;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private Long favoriteCount;
    private Long commentCount;
    private Boolean favorite;
    private List<Map<String, Object>> commentList;
    private Map<String, Integer> pageList;

    public PostDetailResponseDto(Post entity) {
        this.id = entity.getId();
        this.nickname = entity.getMember().getNickname();
        this.profilePath = entity.getMember().getProfilePath();
        this.content = entity.getContent();
        this.createdAt = entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        this.postFileList = PostAndFile.postFileList(entity.getPostFileList());
    }

    public void setFavoriteCount(Long favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public void setCommentList(List<Map<String, Object>> commentList) {
        this.commentList = commentList;
    }

    public void setPageList(Map<String, Integer> pageList) {
        this.pageList = pageList;
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

}
