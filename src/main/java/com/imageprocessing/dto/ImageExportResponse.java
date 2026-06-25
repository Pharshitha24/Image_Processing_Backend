package com.imageprocessing.dto;

public record ImageExportResponse(
        String imageId,
        String fileName,
        String format,
        String downloadUrl,
        String message
) {
}