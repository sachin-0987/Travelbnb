package com.travelbnbtest.travelbnbtest.controller;

import com.travelbnbtest.travelbnbtest.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/bucket")
public class BucketController {

    @Autowired
    private BucketService service;

    @PostMapping(path = "/upload/file/{bucketName}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file,
                                        @PathVariable String bucketName){
            return new ResponseEntity<>(service.uploadFile(file, bucketName), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/file/{bucketName}/{fileName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteFile(@PathVariable String bucketName, @PathVariable String fileName) {
       return new ResponseEntity<>(service.deleteFile(bucketName, fileName), HttpStatus.OK);
    }

    @GetMapping(path = "/bucket/{bucketName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllFiles(@PathVariable String bucketName) {
        return new ResponseEntity<>(service.getAllFileUrls(bucketName), HttpStatus.OK);
    }

    @PutMapping(path = "/update/file/{bucketName}/{fileName}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateFile(@RequestParam MultipartFile file, @PathVariable String bucketName, @PathVariable String fileName) {
        return new ResponseEntity<>(service.updateFile(file, bucketName, fileName), HttpStatus.OK);
    }
}
