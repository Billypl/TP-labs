package org.example.ImageTransformations;

import java.awt.image.BufferedImage;

public class FlipRightTransformation implements ImageTransformation {
    @Override
    public BufferedImage transform(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        BufferedImage newImage = new BufferedImage(h, w, image.getType());

        for (int y = 0; y < image.getHeight(); y++)
            for (int x = 0; x < image.getWidth(); x++)
            {
                int i1 = w - x - 1;
                int i2 = y;
                int i3 = h - y - 1;
                int i4 = w - x - 1;
                newImage.setRGB(i3, i4, image.getRGB(i1, i2));
            }
        return newImage;
    }
}
