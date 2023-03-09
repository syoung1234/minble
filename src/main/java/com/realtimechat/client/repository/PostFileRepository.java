package com.realtimechat.client.repository;

import java.util.List;

import com.realtimechat.client.domain.Post;
import com.realtimechat.client.domain.PostFile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostFileRepository extends JpaRepository<PostFile, Integer> {
    PostFile findByFilename(String filename);

    List<PostFile> findByPost(Post post);
}
