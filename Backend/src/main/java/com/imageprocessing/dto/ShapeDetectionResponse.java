package com.imageprocessing.dto;

public record ShapeDetectionResponse(
        String imageId,
        String detectedShape,
        int width,
        int height,
        String message
) {
}