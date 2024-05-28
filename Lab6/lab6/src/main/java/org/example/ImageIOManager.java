package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

import static java.lang.StringTemplate.STR;

public class  ImageIOManager {

    public static void save(BufferedImage image, Path path) {
        String[] splitPath = path.toString().split("\\.");
        String extension = splitPath[splitPath.length - 1];

        try {
            ImageIO.write(image, extension, path.toFile());
        } catch (IOException e) {
            throw new RuntimeException(STR."Something went wrong! Path: \{path}" ,e);
        }
    }
    public static BufferedImage load(Path path) {
        try {
            return ImageIO.read(path.toFile());
        } catch (IOException e) {
            throw new RuntimeException(STR."Something went wrong! Path: \{path}" ,e);
        }
    }
}
