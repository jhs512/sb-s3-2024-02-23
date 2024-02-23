package com.ll.sbs320240223.domain.home.home.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final AmazonS3 amazonS3;

    @GetMapping("/")
    public List<String> listBuckets() {
        // S3 버킷 리스트를 가져옵니다.
        List<Bucket> buckets = amazonS3.listBuckets();

        // 버킷 이름만 추출하여 리스트로 반환합니다.
        return buckets.stream().map(Bucket::getName).collect(Collectors.toList());
    }

    @GetMapping("/upload")
    public String showUpload() {
        return """
                <form action="/upload" method="post" enctype="multipart/form-data">
                    <input type="file" name="file" accept="image/*">
                    <input type="submit" value="Upload">
                </form>
                """;
    }

    @PostMapping("/upload")
    @ResponseBody
    public String showUpload(
            MultipartFile file
    ) throws IOException {
        // 파일을 S3에 업로드합니다.
        // ObjectMetadata 객체 생성 및 설정
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        // PutObjectRequest 객체 생성
        PutObjectRequest putObjectRequest = new PutObjectRequest(
                "dev-bucket-jhs512-1",
                "test/" + file.getOriginalFilename(),
                file.getInputStream(),
                objectMetadata
        );

        // Amazon S3에 파일 업로드
        amazonS3.putObject(putObjectRequest);

        return "Upload Success";
    }
}

