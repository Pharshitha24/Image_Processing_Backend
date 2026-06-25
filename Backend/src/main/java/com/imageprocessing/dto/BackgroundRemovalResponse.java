package com.imageprocessing.dto;

public record BackgroundRemovalResponse(
        String imageId,
        int width,
        int height,
        String message
) {
}