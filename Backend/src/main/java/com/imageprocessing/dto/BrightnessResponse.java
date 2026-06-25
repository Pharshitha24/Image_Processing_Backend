package com.imageprocessing.dto;

public record BrightnessResponse(
        String imageId,
        int width,
        int height,
        String message
) {
}