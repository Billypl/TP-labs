package org.example;

import org.example.ImageTransformations.FlipRightTransformation;
import org.example.ImageTransformations.ImageTransformation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        String path = "C:\\Users\\kripe\\Desktop\\Uczelnia\\sem 4\\PT\\PT Lab\\Lab6\\lab6\\src\\main\\resources";
        ImageTransformation transformation = new FlipRightTransformation();
        processImages(path, transformation);
    }

    private static void processImages(String folderPath, ImageTransformation transformation) {
        List<Path> files;
        Path source = Path.of(folderPath);
        try(Stream<Path> stream = Files.list(source)) {
            files = stream.toList();
            System.out.println("Test 1 - sequential");
            measureExecutionTime(() -> processImagesSequential(folderPath, files, transformation));
            System.out.println("Test 2 - parallel (default threads count - broken)");
            measureExecutionTime(() -> processImagesParallel(folderPath, files, transformation, -1));
            System.out.println("Test 3 - parallel (1)");
            measureExecutionTime(() -> processImagesParallel(folderPath, files, transformation, 1));
            System.out.println("Test 4 - parallel (2)");
            measureExecutionTime(() -> processImagesParallel(folderPath, files, transformation, 2));
            System.out.println("Test 5 - parallel (4)");
            measureExecutionTime(() -> processImagesParallel(folderPath, files, transformation, 4));
            System.out.println("Test 6 - parallel (8)");
            measureExecutionTime(() -> processImagesParallel(folderPath, files, transformation, 8));
            System.out.println("Test 7 - parallel (16)");
            measureExecutionTime(() -> processImagesParallel(folderPath, files, transformation, 16));
            System.out.println("Test 8 - parallel (32)");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void measureExecutionTime(Runnable function) {
        long time = System.currentTimeMillis();
        function.run();
        System.out.println(STR."\{(System.currentTimeMillis() - time) / 1000.0}s");
    }

    private static void processImagesSequential(String folderPath, List<Path> files, ImageTransformation transformation) {
        createStreamOfImagesToBeProcessed(files, transformation)
            .forEach(image -> ImageIOManager.save(image.data(), Path.of(folderPath + "\\after\\" + image.name())));
    }

    private static void processImagesParallel(String folderPath, List<Path> files, ImageTransformation transformation, int threadsCount) {
        try(ForkJoinPool fjp = threadsCount > 0 ? new ForkJoinPool(threadsCount) : ForkJoinPool.commonPool()) {
            fjp.submit(() ->
                createStreamOfImagesToBeProcessed(files, transformation)
                    .parallel()
                    .forEach(image -> ImageIOManager.save(image.data(), Path.of(folderPath + "\\after\\" + image.name())))
            );
        }
    }

    private static Stream<Image> createStreamOfImagesToBeProcessed(List<Path> files, ImageTransformation transformation) {
        return files.stream()
            .filter(path -> path.toFile().isFile())
            .map(filePath -> new Image(filePath.getFileName().toString(), ImageIOManager.load(filePath)))
            .map(image -> new Image(image.name(), transformation.transform(image.data())));
    }

}