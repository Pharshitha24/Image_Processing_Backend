package com.imageprocessing.service;

import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ImageStore {

    private final Map<String, BufferedImage> images =
            new ConcurrentHashMap<>();

    private final Map<String, String> parentImages =
            new ConcurrentHashMap<>();

    public String save(
            BufferedImage image) {

        String imageId =
                UUID.randomUUID().toString();

        images.put(
                imageId,
                image);

        return imageId;
    }

    public String saveProcessedImage(
            String parentImageId,
            BufferedImage image) {

        String newImageId =
                UUID.randomUUID().toString();

        images.put(
                newImageId,
                image);

        parentImages.put(
                newImageId,
                parentImageId);

        return newImageId;
    }

    public BufferedImage get(
            String imageId) {

        return images.get(imageId);
    }

    public String getParentImageId(
            String imageId) {

        return parentImages.get(imageId);
    }

    public void delete(
            String imageId) {

        images.remove(imageId);
        parentImages.remove(imageId);
    }
}