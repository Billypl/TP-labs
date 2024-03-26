package org.example;

import org.example.input.FileLogger;

import java.util.ArrayList;
import java.util.List;

public class ResultCollector<T> {
    private final List<T> results = new ArrayList<>();
    public synchronized void addResult(T result) {
        results.add(result);

        String contentToAppend = result.toString();
        String[] parts = result.toString().split(",");
        String number = parts[parts.length - 1];
        FileLogger.write(STR."\{number}: \{contentToAppend}\{'\n'}", true);
    }
    public synchronized T getResult(int index) {
        return results.get(index);
    }
    public synchronized List<T> getResults() { return results;}
}
