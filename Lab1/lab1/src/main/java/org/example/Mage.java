package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;

public class Mage implements Comparable<Mage> {
    private String name;
    private int level;
    private double power;
    private Set<Mage> apprentices;

    public static String sortType;


    public Mage(String name, int level, double power) {
        this.name = name;
        this.level = level;
        this.power = power;
        this.apprentices = SetFactory.createSet(sortType);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public Set<Mage> getApprentices() {
        return apprentices;
    }

    public void setApprentices(Set<Mage> apprentices) {
        this.apprentices = apprentices;
    }

    @Override
    public int compareTo(Mage other) {
        return compareToByName(other) != 0 ? compareToByName(other) :
                compareToByLevel(other) != 0 ? -1 * compareToByLevel(other) :
                        -1 * compareToByPower(other);
    }

    public int compareToByName(Mage other) {
        return this.name.compareTo(other.name);
    }

    public int compareToByLevel(Mage other) {
        return Integer.compare(this.level, other.level);
    }

    public int compareToByPower(Mage other) {
        return Double.compare(this.power, other.power);
    }

    @Override
    public String toString() {
        return String.format("Mage {name='%s', level=%s, power=%s}", name, level, power);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }

        Mage other = (Mage) obj;
        return this.name.equals(other.name) &&
                this.level == other.level &&
                this.power == other.power;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, level, power);
    }
}

class MageCompare implements Comparator<Mage> {
    public enum MageField {
        Name,
        Level,
        Power
    }

    private final ArrayList<MageField> fieldsCompareOrder;

    MageCompare(ArrayList<MageField> fieldsCompareOrder) {
        this.fieldsCompareOrder = fieldsCompareOrder;
    }


    @Override
    public int compare(Mage o1, Mage o2) {
        int result = 0;
        for (int i = 0; i < fieldsCompareOrder.size() && result == 0; i++) {
            result = compareResult(fieldsCompareOrder.get(i), o1, o2);
        }
        return result;
    }

    private int compareResult(MageField fieldType, Mage o1, Mage o2) {
        if (fieldType == MageField.Name) {
            return o1.compareToByName(o2);
        } else if (fieldType == MageField.Level) {
            return o1.compareToByLevel(o2) * -1;
        } else {
            return o1.compareToByPower(o2) * -1;
        }
    }
}