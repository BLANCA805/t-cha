package com.tcha.utils.upload.controller;

import com.tcha.utils.upload.service.S3Uploader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public List<String> postImage(
            @RequestPart(value = "images", required = false) MultipartFile[] images
    ) throws IOException {
        String[] imgPaths = null;
        List<String> imgList = new ArrayList<>();

        if (images != null) {
            imgPaths = s3Uploader.upload(images, "image");
            for (String s : imgPaths) {
                imgList.add(s);
            }
        }
        return imgList;
        }

    @DeleteMapping("/images")
    public ResponseEntity deleteImage(
            @RequestParam String image
    ) throws IOException {
        if (image != null) {
                s3Uploader.delete(image);
            }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

        @PostMapping("/videos")
    public List<String> postVideo(
            @RequestPart(value = "videos", required = false) MultipartFile[] videos
    ) throws IOException {

        String[] videoPaths = null;
        List<String> videoList = new ArrayList<>();
            if (videos != null) {
                videoPaths = s3Uploader.upload(videos, "video");
                for (String s : videoPaths) {
                    videoList.add(s);
                }
            }
        return videoList;
        }

    @DeleteMapping("/videos")
    public ResponseEntity deleteVideo(
            @RequestParam String video
    ) throws IOException {
        if (video != null) {
            s3Uploader.delete(video);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
