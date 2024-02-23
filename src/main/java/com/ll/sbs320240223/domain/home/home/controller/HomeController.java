package com.ll.sbs320240223.domain.home.home.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

