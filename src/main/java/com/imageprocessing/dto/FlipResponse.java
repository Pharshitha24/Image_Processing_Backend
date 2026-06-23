package com.imageprocessing.dto;

public record FlipResponse(
        String imageId,
        int width,
        int height,
        String message
) {
}