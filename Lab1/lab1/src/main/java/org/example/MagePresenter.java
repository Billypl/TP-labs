package org.example;

import java.util.ArrayList;
import java.util.Set;

public class MagePresenter {

    private final Set<Mage> mags;
    private String depthIndicator;

    public MagePresenter(Set<Mage> mags) {
        this.mags = mags;
        this.depthIndicator = "";
    }

    public void printMagsTree() {
        ArrayList<Mage> masters = new ArrayList<>(mags);
        masters.removeAll(findApprentices());
        for (Mage m : masters) {
            printMagsTreeHelper(m);
        }
    }

    private void printMagsTreeHelper(Mage mage) {
        depthIndicator = depthIndicator.concat("-");
        System.out.println(depthIndicator + mage.toString());
        for (Mage m : mage.getApprentices()) {
            printMagsTreeHelper(m);
        }
        depthIndicator = depthIndicator.substring(0, depthIndicator.length() - 1);
    }

    private ArrayList<Mage> findApprentices() {
        ArrayList<Mage> apprentices = new ArrayList<>();
        for (Mage m : mags) {
            findApprenticesHelper(m, apprentices);
        }
        return apprentices;
    }

    private void findApprenticesHelper(Mage mage, ArrayList<Mage> apprentices) {
        if (apprentices.contains(mage)) {
            return;
        }

        for (Mage m : mage.getApprentices()) {
            if (!m.getApprentices().isEmpty()) {
                findApprenticesHelper(m, apprentices);
            }
            apprentices.add(m);
        }
    }
}
