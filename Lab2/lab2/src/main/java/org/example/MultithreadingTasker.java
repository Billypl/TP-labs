package org.example;

import org.example.taskTypes.DividersSequenceGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MultithreadingTasker<ResultT> {

    public MultithreadingTasker(int threadsCount, List<Long> initialNumbers) {
        this.threadsCount = threadsCount;
        this.initialNumbers = initialNumbers;
    }

    private final List<Long> initialNumbers;
    private final int threadsCount;

    private boolean isExitRequested = false;

    ArrayList<Thread> threads;
    ArrayList<ThreadTaskHandler<ResultT>> threadsRunnable = new ArrayList<>();
    TaskQueue<ResultT> taskQueue = new TaskQueue<ResultT>();
    ResultCollector<ResultT> resultCollector = new ResultCollector<ResultT>();

    void run() {

        threads = createThreads();

        for(Long number : initialNumbers) {
            Task<ResultT> task = (Task<ResultT>) new DividersSequenceGenerator((long)number);
            taskQueue.addTask(task);
        }

        Scanner scanner = new Scanner(System.in);
        while (!isExitRequested) {
            readNumbersAndAddThemToTaskList(scanner);
        }
        scanner.close();

        for(ThreadTaskHandler<ResultT> runnable : threadsRunnable) {
            runnable.stop();
        }
        taskQueue.shutdown(); // nie działa - nie powiadamia wszystkich wątków o konieczności pobudki
        // też nie działa
//        for(Thread thread : threads) {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }

        System.out.println("All results: ");
        for(ResultT result : resultCollector.getResults()) {
            System.out.println(result.toString());
        }
    }

    private ArrayList<Thread> createThreads() {
        ArrayList<Thread> tmpThreads = new ArrayList<>();
        for (int i = 0; i < threadsCount; i++) {
            ThreadTaskHandler<ResultT> threadRunnable = new ThreadTaskHandler<ResultT>(taskQueue, resultCollector);
            threadsRunnable.add(threadRunnable);
            Thread thread = new Thread(threadRunnable);
            tmpThreads.add(thread);
            thread.start();
        }
        return tmpThreads;
    }
    private void readNumbersAndAddThemToTaskList(Scanner scanner) {
        System.out.println("Provide a number:");
        String input = scanner.next();
        if (input.equals("exit")) {
            isExitRequested = true;
            return;
        }
        try {
            Task<ResultT> task = (Task<ResultT>) new DividersSequenceGenerator(Long.parseLong(input));
            taskQueue.addTask(task);
        } catch (NumberFormatException e) {
            System.out.println("Not a number!");
        }
    }
}
