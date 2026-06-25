package com.imageprocessing.dto;

public record ContrastResponse(
        String imageId,
        int width,
        int height,
        String message
) {
}