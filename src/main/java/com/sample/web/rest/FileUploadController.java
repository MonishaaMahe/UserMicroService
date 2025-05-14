package com.sample.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import org.springframework.http.ResponseEntity;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FileUploadController {

    private final S3Client s3Client;
    @Value("${aws.s3.bucket-name}")
    private String bucket;
    @Value("${aws.s3.input-folder}")
    private String inputFolder;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam MultipartFile file) throws IOException {
        String key = inputFolder + file.getOriginalFilename();
        s3Client.putObject(PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build(),
            RequestBody.fromBytes(file.getBytes()));
        return ResponseEntity.ok("File uploaded to S3: " + key);
    }
}

