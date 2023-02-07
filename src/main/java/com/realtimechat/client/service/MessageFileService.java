package com.realtimechat.client.service;

import java.io.File;

import com.realtimechat.client.dto.request.MessageFileRequestDto;
import com.realtimechat.client.repository.MessageFileRepository;
import com.realtimechat.client.util.CreateFileName;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MessageFileService {

    private final MessageFileRepository messageFileRepository;
    
    // 메시지 파일 저장
    public String save(MultipartFile file) {
        try {
            String originalFileName = file.getOriginalFilename();
            String filename = new CreateFileName(originalFileName).toString();
            Long fileSize = file.getSize();
            String fileType = file.getContentType();
            String folder = "/files/message";
            String savePath = System.getProperty("user.dir") + folder;
            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();
                } catch(Exception e) {
                    e.getStackTrace();
                }
            }

            String filePath = folder + "/" + filename;
            file.transferTo(new File(savePath + "/" + filename));

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
