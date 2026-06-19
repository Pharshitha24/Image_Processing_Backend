package com.imageprocessing.controller;

import com.imageprocessing.dto.BrightnessRequest;
import com.imageprocessing.dto.BrightnessResponse;
import com.imageprocessing.dto.UploadResponse;
import com.imageprocessing.service.ImageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ImageController {

    @GetMapping("/hello")
    public String hello() {
        return "Image Processing Project Started";
    }
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public UploadResponse uploadImage(
            @RequestParam("file") MultipartFile file)
            throws IOException {

        return imageService.uploadImage(file);
    }

    @PostMapping("/{imageId}/brightness")
    public BrightnessRequest adjustBrightness(
            @RequestParam("file") MultipartFile file,
            @RequestParam("imageId") String imageId,
            @RequestParam("brightness") int brightness) {
        return imageService.adjustBrightness(
                imageId,
                brightness
        );
//        return "Brightness adjusted successfully";
    }

    @GetMapping("/brightnessResponse")
    public BrightnessResponse adjustBrightnessresponse(){
        return imageService.adjustBrightnessresponse();
    }
}