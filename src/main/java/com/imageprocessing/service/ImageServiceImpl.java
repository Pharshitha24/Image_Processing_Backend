package com.imageprocessing.service;

import com.imageprocessing.dto.*;
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

    @Override
    public BrightnessResponse adjustBrightnessresponse(
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

        int totalPixels = width * height;

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

            int r = PixelUtil.red(rgb);
            int g = PixelUtil.green(rgb);
            int b = PixelUtil.blue(rgb);

            r += brightness;
            g += brightness;
            b += brightness;

            int newRgb =
                    PixelUtil.rgb(r, g, b);

            image.setRGB(x, y, newRgb);
        }

        imageStore.update(
                imageId,
                image);

        return new BrightnessResponse(
                imageId,
                image.getWidth(),
                image.getHeight(),
                "Brightness adjusted successfully"
        );
    }

    @Override
    public ContrastResponse adjustContrastresponse(
            String imageId,
            double contrast) {

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

            r = (int) ((r - 128) * contrast + 128);
            g = (int) ((g - 128) * contrast + 128);
            b = (int) ((b - 128) * contrast + 128);

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

        return new ContrastResponse(
                imageId,
                width,
                height,
                "Contrast adjusted successfully"
        );
    }



    @Override
    public BlurResponse blurImageresponse(
            String imageId,
            int kernelSize) {

        BufferedImage image =
                imageStore.get(imageId);

        if (image == null) {
            throw new RuntimeException(
                    "Image not found");
        }

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage blurredImage =
                new BufferedImage(
                        width,
                        height,
                        BufferedImage.TYPE_INT_ARGB);

        int kernelsize =
                kernelSize;

        int radius =
                kernelsize / 2;

        for (int y = 0; y < height; y++) {

            for (int x = 0; x < width; x++) {

                int redSum = 0;
                int greenSum = 0;
                int blueSum = 0;
                int count = 0;

                for (int ky = -radius;
                     ky <= radius;
                     ky++) {

                    for (int kx = -radius;
                         kx <= radius;
                         kx++) {

                        int nx = x + kx;
                        int ny = y + ky;

                        if (nx >= 0 &&
                                nx < width &&
                                ny >= 0 &&
                                ny < height) {

                            int rgb =
                                    image.getRGB(
                                            nx,
                                            ny);

                            redSum +=
                                    PixelUtil.red(rgb);

                            greenSum +=
                                    PixelUtil.green(rgb);

                            blueSum +=
                                    PixelUtil.blue(rgb);

                            count++;
                        }
                    }
                }

                int avgRed =
                        redSum / count;

                int avgGreen =
                        greenSum / count;

                int avgBlue =
                        blueSum / count;

                int newRgb =
                        PixelUtil.rgb(
                                avgRed,
                                avgGreen,
                                avgBlue);

                blurredImage.setRGB(
                        x,
                        y,
                        newRgb);
            }
        }

        imageStore.update(
                imageId,
                blurredImage);

        return new BlurResponse(
                imageId,
                width,
                height,
                "Blur applied successfully"
        );
    }

    @Override
    public SharpnessResponse enhanceSharpness(
            String imageId,
            double factor) {

        BufferedImage image =
                imageStore.get(imageId);

        if (image == null) {
            throw new RuntimeException(
                    "Image not found");
        }

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage sharpenedImage =
                new BufferedImage(
                        width,
                        height,
                        BufferedImage.TYPE_INT_ARGB);

        for (int y = 1;
             y < height - 1;
             y++) {

            for (int x = 1;
                 x < width - 1;
                 x++) {

                int center =
                        image.getRGB(x, y);

                int top =
                        image.getRGB(x, y - 1);

                int bottom =
                        image.getRGB(x, y + 1);

                int left =
                        image.getRGB(x - 1, y);

                int right =
                        image.getRGB(x + 1, y);

                double strength = factor;

                int r = (int)(
                        PixelUtil.red(center)
                                + strength * (
                                PixelUtil.red(center) -
                                        (
                                                PixelUtil.red(top)
                                                        + PixelUtil.red(bottom)
                                                        + PixelUtil.red(left)
                                                        + PixelUtil.red(right)
                                        ) / 4.0
                        )
                );

                int g = (int)(
                        PixelUtil.green(center)
                                + strength * (
                                PixelUtil.green(center) -
                                        (
                                                PixelUtil.green(top)
                                                        + PixelUtil.green(bottom)
                                                        + PixelUtil.green(left)
                                                        + PixelUtil.green(right)
                                        ) / 4.0
                        )
                );

                int b = (int)(
                        PixelUtil.blue(center)
                                + strength * (
                                PixelUtil.blue(center) -
                                        (
                                                PixelUtil.blue(top)
                                                        + PixelUtil.blue(bottom)
                                                        + PixelUtil.blue(left)
                                                        + PixelUtil.blue(right)
                                        ) / 4.0
                        )
                );

                int newRgb =
                        PixelUtil.rgb(
                                r,
                                g,
                                b);

                sharpenedImage.setRGB(
                        x,
                        y,
                        newRgb);
            }
        }

        imageStore.update(
                imageId,
                sharpenedImage);

        return new SharpnessResponse(
                imageId,
                width,
                height,
                "Sharpness enhanced successfully"
        );
    }

}

