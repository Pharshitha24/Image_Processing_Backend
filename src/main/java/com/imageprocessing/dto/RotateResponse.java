package com.imageprocessing.dto;

public record RotateResponse(
        String imageId,
        int width,
        int height,
        String message
) {
}