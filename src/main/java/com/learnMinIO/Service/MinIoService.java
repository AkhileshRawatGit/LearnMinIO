package com.learnMinIO.Service;

import io.minio.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class MinIoService {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String buckedName;

    //create bucket if not exist
    public void createBucket() throws Exception{
        boolean isFound=minioClient.bucketExists(
                BucketExistsArgs.builder().bucket(buckedName).build()
        );
        if(!isFound){
            minioClient.makeBucket(
                    MakeBucketArgs.builder().bucket(buckedName).build()
            );
        }

    }

    //file upload
    public String uploadFile(MultipartFile file) throws Exception{
        createBucket();
        String originalFileName=file.getOriginalFilename();
        String extention=originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName= UUID.randomUUID().toString()+extention;

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(buckedName)
                        .object(fileName)
                        .stream(file.getInputStream(),file.getSize(),-1)
                        .contentType(file.getContentType())
                        .build()
        );
        return fileName;
    }

    //get file
    public String getFile(String fileName) throws Exception{
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .bucket(buckedName)
                        .object(fileName)
                        .method(Method.GET)
                        .expiry(60*60*24)
                        .build()
        );
    }

    //delete file
    public void deleteFile(String fileName) throws Exception{
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(buckedName)
                        .object(fileName)
                        .build()
        );
    }
}
