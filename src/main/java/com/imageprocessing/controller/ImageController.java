package com.imageprocessing.controller;

import com.imageprocessing.dto.*;
import com.imageprocessing.service.ImageService;
import com.imageprocessing.service.ImageStore;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

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
            @RequestParam("file") MultipartFile file,
            @RequestParam("imageId") String imageId,
            @RequestParam("brightness") int brightness) {
        return imageService.adjustBrightnessresponse(
                imageId,
                brightness
        );
//        return "Brightness adjusted successfully";
    }

    @GetMapping("/image/{imageId}")
    public ResponseEntity<byte[]> getImage(
            @PathVariable String imageId)
            throws IOException {

        BufferedImage image =
                imageStore.get(imageId);

        ByteArrayOutputStream baos =
                new ByteArrayOutputStream();

        ImageIO.write(image, "png", baos);
        System.out.println(
                "Retrieved image: " + imageId);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(baos.toByteArray());
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


}