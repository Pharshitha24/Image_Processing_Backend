package com.imageprocessing.controller;

import com.imageprocessing.dto.*;
import com.imageprocessing.service.ImageService;
import com.imageprocessing.service.ImageStore;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:5173")


@RestController
@RequestMapping("/api")
public class ImageController {

    private final ImageService imageService;
    private final ImageStore imageStore;

    public ImageController(
            ImageService imageService,
            ImageStore imageStore) {

        this.imageService = imageService;
        this.imageStore = imageStore;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Image Processing Project Started";
    }

    @PostMapping("/upload")
    public UploadResponse uploadImage(
            @RequestParam("file") MultipartFile file)
            throws IOException {

        return imageService.uploadImage(file);
    }

    @PostMapping("/{imageId}/brightness")
    public BrightnessResponse adjustBrightnessresponse(
            @PathVariable String imageId,
            @RequestParam int brightness) {

        return imageService.adjustBrightnessresponse(
                imageId,
                brightness
        );
    }

    @GetMapping("/image/{imageId}")
    public ResponseEntity<byte[]> getImage(
            @PathVariable String imageId)
            throws IOException {

        BufferedImage image =
                imageStore.get(imageId);

        if (image == null) {

            throw new RuntimeException(
                    "Image not found: " + imageId);
        }

        ByteArrayOutputStream baos =
                new ByteArrayOutputStream();

        ImageIO.write(
                image,
                "png",
                baos);

        return ResponseEntity.ok()
                .contentType(
                        MediaType.IMAGE_PNG)
                .body(
                        baos.toByteArray());
    }

    @PostMapping("/{imageId}/contrast")
    public ContrastResponse adjustContrastresponse(
            @PathVariable String imageId,
            @RequestParam double contrast) {

        return imageService.adjustContrastresponse(
                imageId,
                contrast
        );
    }

    @PostMapping("/{imageId}/blur")
    public BlurResponse blurImageresponse(
            @PathVariable String imageId,
            @RequestParam int kernelsize) {

        return imageService.blurImageresponse(
                imageId,
                kernelsize
        );
    }

    @PostMapping("/{imageId}/sharpness")
    public SharpnessResponse enhanceSharpness(
            @PathVariable String imageId,
            @RequestParam double factor) {

        return imageService.enhanceSharpness(
                imageId,
                factor
        );
    }

    @PostMapping("/{imageId}/rotate")
    public RotateResponse rotateImage(
            @PathVariable String imageId,
            @RequestParam int angle) {

        return imageService.rotateImage(
                imageId,
                angle
        );
    }

    @PostMapping("/{imageId}/flip")
    public FlipResponse flipImage(
            @PathVariable String imageId,
            @RequestParam String direction) {

        return imageService.flipImage(
                imageId,
                direction
        );
    }

    @PostMapping("/{imageId}/background-remove")
    public BackgroundRemovalResponse removeBackground(
            @PathVariable String imageId,
            @RequestParam int threshold) {

        return imageService.removeBackground(
                imageId,
                threshold);
    }

    @PostMapping("/{imageId}/grayscale")
    public GrayscaleResponse convertToGrayscale(
            @PathVariable String imageId) {

        return imageService.convertToGrayscale(
                imageId);
    }

    @PostMapping("/{imageId}/shape-detection")
    public ShapeDetectionResponse detectShape(
            @PathVariable String imageId) {

        return imageService.detectShape(
                imageId);
    }

    @PostMapping("/{imageId}/zoom")
    public ZoomResponse zoomImage(
            @PathVariable String imageId,
            @RequestParam double factor) {

        return imageService.zoomImage(
                imageId,
                factor);
    }

    @PostMapping("/layer")
    public LayerResponse layerImages(
            @RequestParam String backgroundImageId,
            @RequestParam MultipartFile foregroundFile,
            @RequestParam int xOffset,
            @RequestParam int yOffset)
            throws IOException {

        return imageService.layerImages(
                backgroundImageId,
                foregroundFile,
                xOffset,
                yOffset
        );
    }
    @PostMapping("/{imageId}/undo")
    public UndoResponse undo(
            @PathVariable String imageId) {

        return imageService.undo(
                imageId);
    }

    @GetMapping(
            "/download/{imageId}"
    )
    public ResponseEntity<byte[]> downloadImage(
            @PathVariable String imageId)
            throws IOException {

        byte[] imageBytes =
                imageService.downloadImage(
                        imageId);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=image.png")
                .contentType(
                        MediaType.IMAGE_PNG)
                .body(
                        imageBytes);
    }

}