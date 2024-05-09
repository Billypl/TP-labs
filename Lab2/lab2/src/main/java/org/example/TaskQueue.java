package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class TaskQueue<T> {
    final private Queue<Task<T>> queue = new LinkedList<>();

    public synchronized void addTask(Task<T> task) {
        queue.add(task);
        notify();
    }
    public synchronized  Task<T> getTask() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        return queue.poll();
    }

    public synchronized void shutdown() {
        notifyAll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
