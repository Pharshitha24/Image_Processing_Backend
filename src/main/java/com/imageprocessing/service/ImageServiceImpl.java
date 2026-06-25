package com.imageprocessing.service;

import com.imageprocessing.dto.*;
import com.imageprocessing.util.ImageUtil;
import com.imageprocessing.util.LinearIndexUtil;
import com.imageprocessing.util.PixelUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;

import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.image.BufferedImage;

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

        BufferedImage processedImage =
                new BufferedImage(
                        width,
                        height,
                        BufferedImage.TYPE_INT_ARGB);

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

            processedImage.setRGB(
                    x,
                    y,
                    newRgb);
        }

        String newImageId =
                imageStore.saveProcessedImage(
                        imageId,
                        processedImage);

        return new BrightnessResponse(
                newImageId,
                width,
                height,
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

        BufferedImage processedImage =
                new BufferedImage(
                        width,
                        height,
                        BufferedImage.TYPE_INT_ARGB);

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

            processedImage.setRGB(
                    x,
                    y,
                    newRgb);
        }

        String newImageId =
                imageStore.saveProcessedImage(
                        imageId,
                        processedImage);

        return new ContrastResponse(
                newImageId,
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

        int radius =
                kernelSize / 2;

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

        String newImageId =
                imageStore.saveProcessedImage(
                        imageId,
                        blurredImage);

        return new BlurResponse(
                newImageId,
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

        String newImageId =
                imageStore.saveProcessedImage(
                        imageId,
                        sharpenedImage);

        return new SharpnessResponse(
                newImageId,
                width,
                height,
                "Sharpness enhanced successfully"
        );
    }

    @Override
    public RotateResponse rotateImage(
            String imageId,
            int angle) {

        BufferedImage image =
                imageStore.get(imageId);

        if (image == null) {
            throw new RuntimeException(
                    "Image not found");
        }

        int width =
                image.getWidth();

        int height =
                image.getHeight();

        BufferedImage rotatedImage;

        if (angle == 90) {

            rotatedImage =
                    new BufferedImage(
                            height,
                            width,
                            BufferedImage.TYPE_INT_ARGB);

            for (int y = 0; y < height; y++) {

                for (int x = 0; x < width; x++) {

                    rotatedImage.setRGB(
                            y,
                            width - 1 - x,
                            image.getRGB(x, y));
                }
            }

        }
        else if (angle == 180) {

            rotatedImage =
                    new BufferedImage(
                            width,
                            height,
                            BufferedImage.TYPE_INT_ARGB);

            for (int y = 0; y < height; y++) {

                for (int x = 0; x < width; x++) {

                    rotatedImage.setRGB(
                            width - 1 - x,
                            height - 1 - y,
                            image.getRGB(x, y));
                }
            }

        }
        else if (angle == 270) {

            rotatedImage =
                    new BufferedImage(
                            height,
                            width,
                            BufferedImage.TYPE_INT_ARGB);

            for (int y = 0; y < height; y++) {

                for (int x = 0; x < width; x++) {

                    rotatedImage.setRGB(
                            height - 1 - y,
                            x,
                            image.getRGB(x, y));
                }
            }

        } else {

            throw new RuntimeException(
                    "Only 90, 180 and 270 rotations are supported");
        }

        String newImageId =
                imageStore.saveProcessedImage(
                        imageId,
                        rotatedImage);

        return new RotateResponse(
                newImageId,
                rotatedImage.getWidth(),
                rotatedImage.getHeight(),
                "Image rotated successfully"
        );
    }

    @Override
    public FlipResponse flipImage(
            String imageId,
            String direction) {

        BufferedImage image =
                imageStore.get(imageId);

        if (image == null) {
            throw new RuntimeException(
                    "Image not found");
        }

        int width =
                image.getWidth();

        int height =
                image.getHeight();

        BufferedImage flippedImage =
                new BufferedImage(
                        width,
                        height,
                        BufferedImage.TYPE_INT_ARGB);

        if ("horizontal".equalsIgnoreCase(direction)) {

            for (int y = 0;
                 y < height;
                 y++) {

                for (int x = 0;
                     x < width;
                     x++) {

                    int rgb =
                            image.getRGB(x, y);

                    flippedImage.setRGB(
                            width - 1 - x,
                            y,
                            rgb);
                }
            }

        }
        else if ("vertical".equalsIgnoreCase(direction)) {

            for (int y = 0;
                 y < height;
                 y++) {

                for (int x = 0;
                     x < width;
                     x++) {

                    int rgb =
                            image.getRGB(x, y);

                    flippedImage.setRGB(
                            x,
                            height - 1 - y,
                            rgb);
                }
            }

        }
        else {

            throw new RuntimeException(
                    "Direction must be horizontal or vertical");
        }

        String newImageId =
                imageStore.saveProcessedImage(
                        imageId,
                        flippedImage);

        return new FlipResponse(
                newImageId,
                width,
                height,
                "Image flipped successfully"
        );
    }

    @Override
    public BackgroundRemovalResponse removeBackground(
            String imageId,
            int threshold) {

        BufferedImage inputImage =
                imageStore.get(imageId);

        if (inputImage == null) {
            throw new RuntimeException(
                    "Image not found");
        }

        int width =
                inputImage.getWidth();

        int height =
                inputImage.getHeight();

        BufferedImage outputImage =
                new BufferedImage(
                        width,
                        height,
                        BufferedImage.TYPE_INT_ARGB);

        int backgroundRGB =
                inputImage.getRGB(0, 0);

        int bgRed =
                PixelUtil.red(backgroundRGB);

        int bgGreen =
                PixelUtil.green(backgroundRGB);

        int bgBlue =
                PixelUtil.blue(backgroundRGB);

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
                    inputImage.getRGB(
                            x,
                            y);

            int red =
                    PixelUtil.red(rgb);

            int green =
                    PixelUtil.green(rgb);

            int blue =
                    PixelUtil.blue(rgb);

            int colorDifference =
                    Math.abs(red - bgRed)
                            +
                            Math.abs(green - bgGreen)
                            +
                            Math.abs(blue - bgBlue);

            if (colorDifference <= threshold) {

                outputImage.setRGB(
                        x,
                        y,
                        0x00000000);

            } else {

                outputImage.setRGB(
                        x,
                        y,
                        rgb);
            }
        }

        String newImageId =
                imageStore.saveProcessedImage(
                        imageId,
                        outputImage);

        return new BackgroundRemovalResponse(
                newImageId,
                width,
                height,
                "Background removed successfully"
        );
    }

    @Override
    public GrayscaleResponse convertToGrayscale(
            String imageId) {

        BufferedImage image =
                imageStore.get(imageId);

        if (image == null) {
            throw new RuntimeException(
                    "Image not found");
        }

        int width =
                image.getWidth();

        int height =
                image.getHeight();

        BufferedImage grayscaleImage =
                new BufferedImage(
                        width,
                        height,
                        BufferedImage.TYPE_INT_ARGB);

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
                    image.getRGB(
                            x,
                            y);

            int r =
                    PixelUtil.red(rgb);

            int g =
                    PixelUtil.green(rgb);

            int b =
                    PixelUtil.blue(rgb);

            int gray =
                    (r + g + b) / 3;

            int newRgb =
                    PixelUtil.rgb(
                            gray,
                            gray,
                            gray);

            grayscaleImage.setRGB(
                    x,
                    y,
                    newRgb);
        }

        String newImageId =
                imageStore.saveProcessedImage(
                        imageId,
                        grayscaleImage);

        return new GrayscaleResponse(
                newImageId,
                width,
                height,
                "Converted to grayscale successfully"
        );
    }

    @Override
    public ShapeDetectionResponse detectShape(
            String imageId) {

        BufferedImage image =
                imageStore.get(imageId);

        if (image == null) {
            throw new RuntimeException(
                    "Image not found");
        }

        int width =
                image.getWidth();

        int height =
                image.getHeight();

        int minX = width;
        int minY = height;
        int maxX = 0;
        int maxY = 0;

        boolean foundShape = false;

        for (int y = 0;
             y < height;
             y++) {

            for (int x = 0;
                 x < width;
                 x++) {

                int rgb =
                        image.getRGB(x, y);

                int r =
                        PixelUtil.red(rgb);

                int g =
                        PixelUtil.green(rgb);

                int b =
                        PixelUtil.blue(rgb);

                int brightness =
                        (r + g + b) / 3;

                if (brightness < 128) {

                    foundShape = true;

                    minX =
                            Math.min(minX, x);

                    minY =
                            Math.min(minY, y);

                    maxX =
                            Math.max(maxX, x);

                    maxY =
                            Math.max(maxY, y);
                }
            }
        }

        if (!foundShape) {

            return new ShapeDetectionResponse(
                    imageId,
                    "No Shape Found",
                    width,
                    height,
                    "No detectable shape present");
        }

        int shapeWidth =
                maxX - minX + 1;

        int shapeHeight =
                maxY - minY + 1;

        String detectedShape;

        double ratio =
                (double) shapeWidth /
                        shapeHeight;

        if (ratio > 0.9 &&
                ratio < 1.1) {

            detectedShape =
                    "Square";

        } else {

            detectedShape =
                    "Rectangle";
        }

        return new ShapeDetectionResponse(
                imageId,
                detectedShape,
                width,
                height,
                "Shape detected successfully");
    }

    @Override
    public ZoomResponse zoomImage(
            String imageId,
            double factor) {

        BufferedImage image =
                imageStore.get(imageId);

        if (image == null) {
            throw new RuntimeException(
                    "Image not found");
        }

        if (factor <= 0) {
            throw new RuntimeException(
                    "Zoom factor must be greater than 0");
        }

        int oldWidth =
                image.getWidth();

        int oldHeight =
                image.getHeight();

        int newWidth =
                (int) (oldWidth * factor);

        int newHeight =
                (int) (oldHeight * factor);

        BufferedImage zoomedImage =
                new BufferedImage(
                        newWidth,
                        newHeight,
                        BufferedImage.TYPE_INT_ARGB);

        for (int y = 0;
             y < newHeight;
             y++) {

            for (int x = 0;
                 x < newWidth;
                 x++) {

                int sourceX =
                        (int) (x / factor);

                int sourceY =
                        (int) (y / factor);

                sourceX =
                        Math.min(
                                sourceX,
                                oldWidth - 1);

                sourceY =
                        Math.min(
                                sourceY,
                                oldHeight - 1);

                zoomedImage.setRGB(
                        x,
                        y,
                        image.getRGB(
                                sourceX,
                                sourceY));
            }
        }

        String newImageId =
                imageStore.saveProcessedImage(
                        imageId,
                        zoomedImage);

        return new ZoomResponse(
                newImageId,
                newWidth,
                newHeight,
                "Zoom applied successfully"
        );
    }

    @Override
    public LayerResponse layerImages(
            String backgroundImageId,
            MultipartFile foregroundFile,
            int xOffset,
            int yOffset)
            throws IOException {

        BufferedImage background =
                imageStore.get(backgroundImageId);

        if (background == null) {
            throw new RuntimeException(
                    "Background image not found");
        }

        BufferedImage foreground =
                ImageIO.read(
                        foregroundFile.getInputStream());

        int width =
                background.getWidth();

        int height =
                background.getHeight();

        BufferedImage result =
                new BufferedImage(
                        width,
                        height,
                        BufferedImage.TYPE_INT_ARGB);

        Graphics2D g =
                result.createGraphics();

        // draw background
        g.drawImage(
                background,
                0,
                0,
                null);

        // 50% transparency
        g.setComposite(
                AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER,
                        0.5f
                ));

        // draw foreground
        g.drawImage(
                foreground,
                xOffset,
                yOffset,
                null);

        g.dispose();

        String newImageId =
                imageStore.saveProcessedImage(
                        backgroundImageId,
                        result);

        return new LayerResponse(
                newImageId,
                result.getWidth(),
                result.getHeight(),
                "Images layered successfully"
        );
    }

    @Override
    public UndoResponse undo(
            String imageId) {

        String parentImageId =
                imageStore.getParentImageId(
                        imageId);

        if (parentImageId == null) {

            throw new RuntimeException(
                    "No previous version available");
        }

        BufferedImage image =
                imageStore.get(
                        parentImageId);

        return new UndoResponse(
                parentImageId,
                image.getWidth(),
                image.getHeight(),
                "Undo successful"
        );
    }

    @Override
    public byte[] downloadImage(
            String imageId)
            throws IOException {

        BufferedImage image =
                imageStore.get(imageId);

        if (image == null) {
            throw new RuntimeException(
                    "Image not found");
        }

        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();

        ImageIO.write(
                image,
                "png",
                outputStream);

        return outputStream.toByteArray();
    }

}

