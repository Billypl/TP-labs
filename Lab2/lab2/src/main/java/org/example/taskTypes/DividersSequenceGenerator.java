package org.example.taskTypes;

import org.example.Task;

import java.util.ArrayList;

public class DividersSequenceGenerator implements Task<ArrayList<Long>> {

    private Long number;

    public DividersSequenceGenerator(Long number) {
        this.number = number;
    }

    @Override
    public ArrayList<Long> execute() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Long> dividers = new ArrayList<>();
        for (long i = 1; i <= number; i++) {
            if (number % i == 0) {
                dividers.add(i);
            }
        }
        System.out.println(dividers.toString());
        return dividers;
    }
}
