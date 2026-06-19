package com.imageprocessing.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageUtil {

    private ImageUtil() {
    }

    public static BufferedImage read(MultipartFile file)
            throws IOException {

        return ImageIO.read(file.getInputStream());
    }

    public static BufferedImage copy(BufferedImage source) {

        BufferedImage copy = new BufferedImage(
                source.getWidth(),
                source.getHeight(),
                source.getType()
        );

        copy.setData(source.getData());

        return copy;
    }

    public static byte[] toByteArray(
            BufferedImage image,
            String format)
            throws IOException {

        ByteArrayOutputStream baos =
                new ByteArrayOutputStream();

        ImageIO.write(image, format, baos);

        return baos.toByteArray();
    }
}