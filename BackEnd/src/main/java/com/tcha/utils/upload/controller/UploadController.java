package com.tcha.utils.upload.controller;

import com.tcha.utils.upload.service.S3Uploader;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
@Validated
@Slf4j
@RequiredArgsConstructor
public class UploadController {
    private final S3Uploader s3Uploader;

    @PostMapping("/images")
    public String[] postImage(
            @RequestPart(value = "images", required = false) MultipartFile[] images
    ) throws IOException {
        String[] imgPaths = null;

        if (images != null) {
            imgPaths = s3Uploader.upload(images, "image");
        }

        return imgPaths;
        }

        @PostMapping("/videos")
    public String[] postVideo(
            @RequestPart(value = "videos", required = false) MultipartFile[] videos
    ) throws IOException {

        String[] videoPaths = null;

        if (videos != null) {
            videoPaths = s3Uploader.upload(videos, "video");
        }

        return videoPaths;
        }
}
