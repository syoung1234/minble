package com.realtimechat.client.dto.response;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
// import java.util.stream.Collectors;

import com.realtimechat.client.domain.Post;
import com.realtimechat.client.domain.PostFile;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostResponseDto {
    private Integer id;
    private String content;
    private String nickname;
    private String profilePath;
    private String createdAt;
    private List<PostAndFile> postFileList;
    private Long favoriteCount;
    private Long commentCount;
    private Boolean favorite;

    public PostResponseDto(Post entity) {
        this.id = entity.getId();
        this.nickname = entity.getMember().getNickname();
        this.profilePath = entity.getMember().getProfilePath();
        this.content = entity.getContent();
        this.createdAt = entity.getCreated_at().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.postFileList = PostAndFile.postFileList(entity.getPostFileList());
    }

    public void setFavorite(Long favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
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

            // List<String> new_list = files.stream().map(o->o.getFilename()).collect(Collectors.toList());
            
            return list;
        }

        public PostAndFile(PostFile postFile) {
            this.filename = postFile.getFilename();
            this.filePath = postFile.getFilePath();
        }
    }
   
}
