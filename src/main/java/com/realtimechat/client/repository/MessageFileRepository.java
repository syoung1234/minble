package com.realtimechat.client.repository;

import java.util.Optional;

import com.realtimechat.client.domain.MessageFile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageFileRepository extends JpaRepository<MessageFile, Integer> {
    Optional<MessageFile> findByFilePath(String filePath);
}
