package com.imageprocessing.dto;

public record BlurResponse(
        String imageId,
        int width,
        int height,
        String message
) {
}