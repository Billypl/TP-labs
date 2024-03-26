package org.example.taskTypes;

public class Primer {
    private Pair<Integer, Boolean> primer;

    public Primer(Integer number) {
        this.primer = new Pair<>(number, null);
    }

    public Integer getNumber() {
        return primer.getFirst();
    }
    public Boolean getIsPrime() {
        return primer.getSecond();
    }
    public void setIsPrime(Boolean isPrime) {
        this.primer.setSecond(isPrime);
    }
    public Pair<Integer, Boolean> getPrimer() {
        return primer;
    }

    @Override
    public String toString() {
        return primer.toString();
    }
}
