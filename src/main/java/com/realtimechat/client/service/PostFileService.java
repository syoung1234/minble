package com.realtimechat.client.service;

// import java.io.File;
import java.util.List;

import javax.transaction.Transactional;

import com.realtimechat.client.domain.Post;
import com.realtimechat.client.domain.PostFile;
import com.realtimechat.client.dto.request.PostFileRequestDto;
import com.realtimechat.client.repository.PostFileRepository;
import com.realtimechat.client.util.CreateFileName;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostFileService {
    private final PostFileRepository postFileRepository;
    private final S3Upload s3upload;

    @Value("${app.url}")
    private String appUrl;

    // 이미지 업로드 저장
    @Transactional
    public void uploadSave(List<MultipartFile> files, Post post) {
        try {
            for (MultipartFile file : files) {
                if (file == null) {
                    continue;
                }
                String originalFileName = file.getOriginalFilename();
                String filename = new CreateFileName(originalFileName).toString();
                Long fileSize = file.getSize();
                String fileType = file.getContentType();

                /* 서버 저장 -> s3 저장으로 변경 */
                
                // s3 저장
                String saveFile = "post/" + filename;
                String filePath = s3upload.upload(file, saveFile);

                // 서버 저장
                // String folder = "/files/post";
                // String savePath = System.getProperty("user.dir") + folder;
                // if (!new File(savePath).exists()) {
                //     try {
                //         new File(savePath).mkdir();
                //     } catch(Exception e) {
                //         e.getStackTrace();
                //     }
                // }
                // String filePath = appUrl + folder + "/" + filename;
                // file.transferTo(new File(savePath + "/" + filename));
    
                PostFileRequestDto postFileRequestDto = new PostFileRequestDto();
                postFileRequestDto.setOriginalFileName(originalFileName);
                postFileRequestDto.setFilename(filename);
                postFileRequestDto.setFilePath(filePath);
                postFileRequestDto.setFileSize(fileSize);
                postFileRequestDto.setFileType(fileType);
                postFileRequestDto.setPost(post);
                
                //postFileService.save(postFileRequestDto);
                postFileRepository.save(postFileRequestDto.toEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    // 이미지 삭제
    @Transactional
    public String delete(List<String> deleteList) {
        for (String filename : deleteList) {
            PostFile postFile = postFileRepository.findByFilename(filename);
            s3upload.delete(postFile.getFilePath());
            postFileRepository.delete(postFile);
        }
        return "success";
    }
}
