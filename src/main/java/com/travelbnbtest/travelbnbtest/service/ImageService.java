package com.travelbnbtest.travelbnbtest.service;

import com.travelbnbtest.travelbnbtest.payload.ImageDto;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    ImageDto uploadImage(MultipartFile file, long propertyId, String bucketName);
}
