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

    public String save(BufferedImage image) {

        String imageId =
                UUID.randomUUID().toString();

        images.put(imageId, image);

        return imageId;
    }

    public BufferedImage get(String imageId) {

        return images.get(imageId);
    }

    public void update(
            String imageId,
            BufferedImage image) {

        images.put(imageId, image);
    }

    public void delete(String imageId) {

        images.remove(imageId);
    }
}