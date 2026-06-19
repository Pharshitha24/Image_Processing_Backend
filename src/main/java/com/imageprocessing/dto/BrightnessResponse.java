package com.imageprocessing.dto;

public record BrightnessResponse(
        String imageId,
        String outputFileName,
        int width,
        int height,
        String message
) {
}