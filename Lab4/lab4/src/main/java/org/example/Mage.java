package org.example;

import jakarta.persistence.*;


@Entity
public class Mage {

    @Id
    private String name;
    private int level;

    public Mage() {
    }

    public Mage(String name, int power, Tower tower) {
        this.name = name;
        this.level = power;
        this.tower = tower;
        tower.getMages().add(this);
    }

    @ManyToOne
    private Tower tower;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int power) {
        this.level = power;
    }

    public Tower getTower() {
        return tower;
    }

    public void setTower(Tower tower) {
        this.tower = tower;
    }

    @Override
    public String toString() {
        return "Mage{" +
                "name='" + name + '\'' +
                ", power=" + level +
                ", tower=" + tower +
                '}';
    }
}
