package org.example;

public class ThreadTaskHandler<T> implements Runnable {
    private final TaskQueue<T> taskQueue;
    private final ResultCollector<T> resultCollector;

    private boolean isStopped = false;

    public ThreadTaskHandler(TaskQueue<T> taskQueue, ResultCollector<T> resultCollector) {
        this.taskQueue = taskQueue;
        this.resultCollector = resultCollector;
    }

    public void stop() {
        isStopped = true;
//        taskQueue.shutdown();
//        System.out.println("test");
    }

    @Override
    public void run() {
        while (!isStopped || !taskQueue.isEmpty()) {
            try {
//                System.out.println("get");
                Task<T> task = taskQueue.getTask();
                T result = task.execute();
                resultCollector.addResult(result);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println(STR."exited thread: \{Thread.currentThread().threadId()}");
    }
}
