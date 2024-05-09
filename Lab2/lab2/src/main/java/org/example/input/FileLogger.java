package org.example.input;

import java.io.IOException;

public class FileLogger {

    private static String filename = "result.txt";

    public static String getFilename() {
        return filename;
    }

    public static void setFilename(String filename) {
        FileLogger.filename = filename;
    }

    public static void write(String content, boolean append) {
        try {
            java.io.FileWriter fileWriter = new java.io.FileWriter(filename, append);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing content to the file: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
