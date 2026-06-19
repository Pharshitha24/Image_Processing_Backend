package com.imageprocessing.service;

import com.imageprocessing.dto.BrightnessResponse;
import com.imageprocessing.dto.UploadResponse;
import com.imageprocessing.dto.BrightnessRequest;
import com.imageprocessing.util.ImageUtil;
import com.imageprocessing.util.LinearIndexUtil;
import com.imageprocessing.util.PixelUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageStore imageStore;

    public ImageServiceImpl(ImageStore imageStore) {
        this.imageStore = imageStore;
    }

    @Override
    public UploadResponse uploadImage(
            MultipartFile file)
            throws IOException {

        BufferedImage image =
                ImageUtil.read(file);

        String imageId =
                imageStore.save(image);

        return new UploadResponse(
                imageId,
                image.getWidth(),
                image.getHeight()
        );
    }

    public BrightnessRequest adjustBrightness(
            String imageId,
            int brightness) {

        BufferedImage image =
                imageStore.get(imageId);

        if (image == null) {
            throw new RuntimeException(
                    "Image not found");
        }

        int width = image.getWidth();
        int height = image.getHeight();

        int totalPixels =
                width * height;

        for (int index = 0;
             index < totalPixels;
             index++) {

            int x =
                    LinearIndexUtil.getX(
                            index,
                            width);

            int y =
                    LinearIndexUtil.getY(
                            index,
                            width);

            int rgb =
                    image.getRGB(x, y);

            int r =
                    PixelUtil.red(rgb);

            int g =
                    PixelUtil.green(rgb);

            int b =
                    PixelUtil.blue(rgb);

            r += brightness;
            g += brightness;
            b += brightness;

            int newRgb =
                    PixelUtil.rgb(
                            r,
                            g,
                            b);

            image.setRGB(
                    x,
                    y,
                    newRgb);
        }

        imageStore.update(
                imageId,
                image);
        return null;
    }

    @Override
    public BrightnessResponse adjustBrightnessresponse(
            String imageId,
            int brightness)
            throws IOException {

        // process image
        BufferedImage image =
                imageStore.get(imageId);
        int width = image.getWidth();
        int height = image.getHeight();

        return new BrightnessResponse(
                imageId,
                "brightened.png",
                width,
                height,
                "Brightness adjusted successfully"
        );
    }


}

