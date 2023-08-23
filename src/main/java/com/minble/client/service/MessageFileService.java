package com.minble.client.service;

import com.minble.client.dto.request.MessageFileRequestDto;
import com.minble.client.repository.MessageFileRepository;
import com.minble.client.util.CreateFileName;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MessageFileService {

    private final MessageFileRepository messageFileRepository;
    private final S3Upload s3Upload;
    
    @Value("${app.url}")
    private String appUrl;

    // 메시지 파일 저장
    public String save(MultipartFile file) {
        try {
            String originalFileName = file.getOriginalFilename();
            String filename = new CreateFileName(originalFileName).toString();
            Long fileSize = file.getSize();
            String fileType = file.getContentType();

            String saveFile = "message/" + filename;
            String filePath = s3Upload.upload(file, saveFile);

            MessageFileRequestDto messageFileRequestDto = new MessageFileRequestDto();
            messageFileRequestDto.setOriginalFileName(originalFileName);
            messageFileRequestDto.setFilename(filename);
            messageFileRequestDto.setFilePath(filePath);
            messageFileRequestDto.setFileSize(fileSize);
            messageFileRequestDto.setFileType(fileType);

            messageFileRepository.save(messageFileRequestDto.toEntity());

            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
