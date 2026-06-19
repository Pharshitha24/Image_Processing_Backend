package com.imageprocessing.service;

import com.imageprocessing.dto.BrightnessRequest;
import com.imageprocessing.dto.BrightnessResponse;
import com.imageprocessing.dto.UploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    UploadResponse uploadImage(
            MultipartFile file)
            throws IOException;

    BrightnessRequest adjustBrightness(
            String imageId,
            int brightness);

    BrightnessResponse adjustBrightnessresponse(
            @RequestBody BrightnessRequest request
    );
}