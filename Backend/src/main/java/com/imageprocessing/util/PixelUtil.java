package com.imageprocessing.util;

public class PixelUtil {

    private PixelUtil() {
    }

    public static int red(int rgb) {
        return (rgb >> 16) & 0xFF;
    }

    public static int green(int rgb) {
        return (rgb >> 8) & 0xFF;
    }

    public static int blue(int rgb) {
        return rgb & 0xFF;
    }

    public static int rgb(int r, int g, int b) {

        r = clamp(r);
        g = clamp(g);
        b = clamp(b);

        int a = 255;

        return (a << 24)
                | (r << 16)
                | (g << 8)
                | b;
    }

    public static int clamp(int value) {

        if (value < 0) {
            return 0;
        }

        if (value > 255) {
            return 255;
        }

        return value;
    }
}