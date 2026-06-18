package com.imageprocessing.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    @GetMapping("/hello")
    public String hello() {
        return "Image Processing Project Started";
    }
}