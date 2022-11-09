package com.realtimechat.client.repository;

import com.realtimechat.client.domain.PostFile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostFileRepository extends JpaRepository<PostFile, Integer> {
    PostFile findByFilename(String filename);
}
