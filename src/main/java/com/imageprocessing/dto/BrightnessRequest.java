package com.imageprocessing.dto;

public record BrightnessRequest(
        String imageId,
        int brightness
) {
}