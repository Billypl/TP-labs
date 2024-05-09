package org.example.input;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileInputReader {

    static public List<Long> readInitialNumbersFromFile(String filename) {
        List<Long> numbers = new ArrayList<>();
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Long number = Long.parseLong(line);
                numbers.add(number);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Couldnt find the file: " + filename);
        } catch (NumberFormatException e) {
            System.err.println("Wrond number format in the file: " + filename);
            e.printStackTrace();
        }
        return numbers;
    }

}
