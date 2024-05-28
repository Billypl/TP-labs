package org.example.ImageTransformations;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GrayscaleTransformation implements ImageTransformation {
    @Override
    public BufferedImage transform(BufferedImage image) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color pixelColor = new Color(image.getRGB(i, j));
                // luminous grayscale
                int grayedPixelColor = (int)
                    (
                        pixelColor.getRed() * 0.2126
                        + pixelColor.getGreen() * 0.7152
                        + pixelColor.getBlue() * 0.0722
                    );
                newImage.setRGB(i, j, (new Color(grayedPixelColor, grayedPixelColor, grayedPixelColor)).getRGB());
            }
        }
        return newImage;
    }
}
