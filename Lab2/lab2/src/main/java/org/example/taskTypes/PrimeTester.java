package org.example.taskTypes;

import org.example.Task;

class PrimeTester implements Task<Primer> {

    Primer primer;

    PrimeTester(Integer number) {
        primer = new Primer(number);
    }

    boolean isPrime() {
        if (primer.getNumber() <= 1) {
            return false;
        }
        for (int i = 2; i * i <= primer.getNumber(); i++) {
            if (primer.getNumber() % i == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Primer execute() {
        primer.setIsPrime(isPrime());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(primer.toString());
        return primer;
    }
}
