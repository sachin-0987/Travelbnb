package com.travelbnbtest.travelbnbtest.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BucketService {

    @Autowired
    private AmazonS3 amazonS3;

    public String uploadFile(MultipartFile file,String bucketName){
        if (file.isEmpty()){
            throw new IllegalStateException("cannot upload empty file");
        }
        try {
            File convFile=new File(System.getProperty("java.io.tmpdir")+ "/" + file.getOriginalFilename());
            file.transferTo(convFile);
            try {
                amazonS3.putObject(bucketName,convFile.getName(),convFile);
                return amazonS3.getUrl(bucketName, file.getOriginalFilename()).toString();
            }catch (AmazonS3Exception s3Exception){
                return "unable to upload file :" +s3Exception.getMessage();
            }
        }catch (Exception e){
            throw new IllegalStateException("failed to upload file",e);
        }
    }

    public String deleteFile(String bucketName, String fileName) {
        try {
            // Check if the file exists in the bucket
            if (!amazonS3.doesObjectExist(bucketName, fileName)) {
                return "File does not exist";
            }
            // Delete the file
            amazonS3.deleteObject(bucketName, fileName);
            return "File deleted successfully";
        } catch (AmazonS3Exception e) {
            throw new IllegalStateException("Failed to delete file", e);
        }
    }


    public List<String> getAllFileUrls(String bucketName) {
        try {
            ListObjectsV2Result result = amazonS3.listObjectsV2(bucketName);
            List<S3ObjectSummary> objects = result.getObjectSummaries();
            List<String> urls = new ArrayList<>();
            for (S3ObjectSummary os : objects) {
                urls.add(amazonS3.getUrl(bucketName, os.getKey()).toString());
            }
            return urls;
        } catch (AmazonS3Exception e) {
            throw new IllegalStateException("Failed to list files", e);
        }
    }
    public String updateFile(MultipartFile file, String bucketName, String fileName) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        try {
            // Delete existing file
            amazonS3.deleteObject(bucketName, fileName);
            // Upload new file
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            file.transferTo(convFile);
            amazonS3.putObject(bucketName, convFile.getName(), convFile);
            return amazonS3.getUrl(bucketName, file.getOriginalFilename()).toString();
        } catch (AmazonS3Exception | IOException e) {
            throw new IllegalStateException("Failed to update file", e);
        }
    }

}
