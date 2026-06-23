package com.imageprocessing.dto;

public record ZoomResponse(
        String imageId,
        int width,
        int height,
        String message
) {
}