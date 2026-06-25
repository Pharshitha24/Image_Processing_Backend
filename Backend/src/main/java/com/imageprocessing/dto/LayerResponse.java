package com.imageprocessing.dto;

public record LayerResponse(

        String imageId,

        int width,

        int height,

        String message

) {
}