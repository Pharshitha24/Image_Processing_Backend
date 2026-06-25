package com.imageprocessing.dto;

public record GrayscaleResponse(
        String imageId,
        int width,
        int height,
        String message
) {
}