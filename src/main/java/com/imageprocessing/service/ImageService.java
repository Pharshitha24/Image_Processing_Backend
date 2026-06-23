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

    RotateResponse rotateImage(
            String imageId,
            int angle);

    FlipResponse flipImage(
            String imageId,
            String direction);

    BackgroundRemovalResponse removeBackground(
            String imageId,
            int threshold);

    GrayscaleResponse convertToGrayscale(
            String imageId);

    ShapeDetectionResponse detectShape(
            String imageId);

    ZoomResponse zoomImage(
            String imageId,
            double factor);

    LayerResponse layerImages(
            String backgroundImageId,
            String foregroundImageId,
            int xOffset,
            int yOffset);


}