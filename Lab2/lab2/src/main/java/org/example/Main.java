package org.example;

import org.example.input.FileLogger;
import org.example.input.FileInputReader;

import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        FileLogger.setFilename("result.txt");
        FileLogger.write("", false);

        int numberOfThreads = 3; //Integer.parseInt(args[0]);

        System.out.println(System.getProperty("user.dir"));
        List<Long> numbers1 = FileInputReader.readInitialNumbersFromFile("test 1 watki.txt");
        List<Long> numbers2 = FileInputReader.readInitialNumbersFromFile("test 2 watki.txt");
        List<Long> numbers = new ArrayList<>(numbers1);
        numbers.addAll(numbers2);

        MultithreadingTasker<ArrayList<Long>> tasker = new MultithreadingTasker<>(numberOfThreads, numbers);
        tasker.run();
    }
}