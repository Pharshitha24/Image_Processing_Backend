package com.imageprocessing.dto;

public record SharpnessResponse(
        String imageId,
        int width,
        int height,
        String message
) {
}