package com.tcha.utils.upload.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@RequiredArgsConstructor
@Service
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File file = convert(multipartFile)
                .orElseThrow(() -> new IOException("Failed to create file"));

        return uploadFile(file, dirName);
    }

    public String[] upload(MultipartFile[] multipartFiles, String dirName) throws IOException {
        int fileLength = multipartFiles.length;
        String[] ret = new String[fileLength];

        int cnt = 0;

        try {
            for (int i = 0; i < fileLength; i++) {
                // transactional test
//                if(i == 3) throw new Exception();
                File file = convert(multipartFiles[i])
                        .orElseThrow(() -> new IOException("Failed to create file"));

                ret[i] = uploadFile(file, dirName);

                cnt++;
            }
        } catch (Exception e) {
//            logger.info("Failed to upload file at S3 server");
            for (int i = cnt - 1; i >= 0; i--) {
                deleteFile(ret[i]);
            }
            throw new IOException("Failed to upload file at S3 server");
        }
        return ret;
    }

    public String uploadFile(File file, String dirName) throws SdkClientException {
        String filePath = dirName + "/" + file.getName();

        PutObjectRequest request = new PutObjectRequest(bucket, filePath, file)
                .withCannedAcl(CannedAccessControlList.PublicRead);
        try {
            amazonS3Client.putObject(request);
        } catch (SdkClientException e) {
            throw new SdkClientException("Failed to upload file");
        } finally {
            file.delete();
        }
        return amazonS3Client.getUrl(bucket, filePath).toString();
    }

    public Optional<File> convert(MultipartFile multipartFile) throws IOException {
        // Generate File name
        File retFile = new File(UUID.randomUUID() + "-" + multipartFile.getOriginalFilename());

        if (retFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(retFile)) {
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(retFile);
        }
        return Optional.empty();
    }

    public int delete(String fileURL) {
        try {
            deleteFile(fileURL);
        } catch (UnsupportedEncodingException | ArrayIndexOutOfBoundsException e) {
            return -1;
        }
        return 0;
    }

    public int delete(List<String> fileURLs) {
        int len = fileURLs.size();
        int ret = 0;
        for (int i = 0; i < len; i++) {
            try {
                deleteFile(fileURLs.get(i));
            } catch (UnsupportedEncodingException | ArrayIndexOutOfBoundsException e) {
                ret = -1;
            }
        }

        return ret;
    }

    public void deleteFile(String fileURL)
            throws UnsupportedEncodingException, ArrayIndexOutOfBoundsException {
        String[] urlParts = fileURL.split("/", 4);
        String fileName = urlParts[3];

//        logger.info("Delete file : {}", fileName);
        String objKey = URLDecoder.decode(fileName, "UTF-8").trim();
//        logger.info("Delete object : {}", objKey);
        DeleteObjectRequest request = new DeleteObjectRequest(bucket, objKey);
        amazonS3Client.deleteObject(request);
    }


    public String copyFile(String fileURL, String dstFileName) {
        String srcFile = fileURL.split("/", 4)[3].trim();
        try {
            if (amazonS3Client.doesObjectExist(bucket, srcFile)) {
//                logger.info("Image found from bucket, copy object to name : {}", dstFileName);
                String[] parts = fileURL.split(".");
                String fileExtension = parts[parts.length - 1].trim();

                String dstFile = dstFileName + "." + fileExtension;
                CopyObjectRequest copyObjectRequest = new CopyObjectRequest(bucket, srcFile, bucket,
                        dstFile);

                return amazonS3Client.getUrl(bucket, dstFile).toString();
            } else {
//                logger.info("image is not in S3 bucket, cannot copy file : {}", fileURL);
                return fileURL;
            }
        } catch (AmazonS3Exception e) {
//            logger.info("image URL is not from S3, cannot copy file : {}", fileURL);
            return fileURL;
        }
    }
}

