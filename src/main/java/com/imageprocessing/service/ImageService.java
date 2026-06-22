package com.imageprocessing.service;

import com.imageprocessing.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    UploadResponse uploadImage(
            MultipartFile file)
            throws IOException;


    BrightnessResponse adjustBrightnessresponse(
            String imageId,
            int brightness
    );

    ContrastResponse adjustContrastresponse(
            String imageId,
            double contrast);

    BlurResponse blurImageresponse(
            String imageId,
            int kernelSize);

    SharpnessResponse enhanceSharpness(
            String imageId,
            double factor);


}