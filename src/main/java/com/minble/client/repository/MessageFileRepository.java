package com.minble.client.repository;

import java.util.Optional;

import com.minble.client.domain.MessageFile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageFileRepository extends JpaRepository<MessageFile, Integer> {
    Optional<MessageFile> findByFilePath(String filePath);
    
    MessageFile findByFilename(String filename);
}
