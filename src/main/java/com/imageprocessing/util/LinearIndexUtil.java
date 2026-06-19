package com.imageprocessing.util;

public class LinearIndexUtil {

    private LinearIndexUtil() {
    }

    public static int toIndex(int x, int y, int width) {
        return y * width + x;
    }

    public static int getX(int index, int width) {
        return index % width;
    }

    public static int getY(int index, int width) {
        return index / width;
    }
}