package com.travelbnbtest.travelbnbtest.controller;

import com.travelbnbtest.travelbnbtest.entity.AppUser;
import com.travelbnbtest.travelbnbtest.payload.ImageDto;
import com.travelbnbtest.travelbnbtest.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/image")
public class ImageController {

    private ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(path = "/upload/file/{bucketName}/property/{propertyId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ImageDto> uploadImage(
            @RequestParam MultipartFile file,
            @PathVariable long propertyId,
            @PathVariable String bucketName
         //   @AuthenticationPrincipal AppUser user

            ){
        ImageDto savedImageEntity = imageService.uploadImage(file, propertyId, bucketName);
        return new ResponseEntity<>(savedImageEntity, HttpStatus.CREATED);
    }
}
