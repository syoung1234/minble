package com.minble.client.service;

import java.io.IOException;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

// Post files, Message file, Member - profile file
@RequiredArgsConstructor
@Service
public class S3Upload { 

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;
    
    /*************************
        s3 파일 업로드
    **************************/
    public String upload(MultipartFile multipartFile, String fileName) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getInputStream().available());
        
        amazonS3Client.putObject(bucket, fileName, multipartFile.getInputStream(), objectMetadata);
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }


    /*************************
        s3 파일 삭제
    **************************/
    public void delete(String filePath) {
        try {
            String key = filePath.substring(filePath.lastIndexOf("com/") + 4);
            Boolean exist = amazonS3Client.doesObjectExist(bucket, key); // 존재 여부 
            if (exist == true) { // 존재한다면 삭제
                DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, key);
                amazonS3Client.deleteObject(deleteObjectRequest);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
