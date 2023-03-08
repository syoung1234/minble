package com.realtimechat.client.service;

import java.io.IOException;

import com.amazonaws.services.s3.AmazonS3Client;
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
    
    public String upload(MultipartFile multipartFile, String fileName) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getInputStream().available());
        
        amazonS3Client.putObject(bucket, fileName, multipartFile.getInputStream(), objectMetadata);
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }
}
