 package com.imageprocessing.dto;

public record UndoResponse(
        String imageId,
        int width,
        int height,
        String message) {
}