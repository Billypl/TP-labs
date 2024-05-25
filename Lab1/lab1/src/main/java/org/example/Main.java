package org.example;

import java.util.*;


public class Main {
    private static ArrayList<Mage> createTestData() {
        Mage m1 = new Mage("Andrzej", 11, 4.20);
        Mage m2 = new Mage("Malwem", 11, 6.9);
        Mage m3 = new Mage("Bartek", 33, 2);
        Mage m4 = new Mage("Billy", 44, 21.37);
        Mage m5 = new Mage("Uszatek", 99, 22.5);
        Mage m6 = new Mage("Koralgol", 111, 41);
        Mage m7 = new Mage("Bogdan", 77, 61.2);
        Mage m8 = new Mage("Ojczysty", 88, 1.2);
        Mage m9 = new Mage("Marcin", 66, 15);
        Mage m10 = new Mage("Domino", 55, 1410);
        ArrayList<Mage> mTmp = new ArrayList<>(List.of(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10));

        m7.getApprentices().add(m8);
        m7.getApprentices().add(m9);
        m9.getApprentices().add(m10);

        m4.getApprentices().add(m1);
        m4.getApprentices().add(m2);
        m4.getApprentices().add(m3);
        m2.getApprentices().add(m5);
        m5.getApprentices().add(m6);
        return mTmp;
    }

    private static Map<Mage, Integer> createMap(Set<Mage> mags, String sortType) {
        Map<Mage, Integer> mageMap = MapFactory.createMap(sortType);
        for (Mage m : mags) {
            mageMap.put(m, calculateApprentices(m.getApprentices()));
        }
        return mageMap;
    }

    private static Integer calculateApprentices(Set<Mage> mags) {
        int apprenticesCounter = 0;
        for (Mage m : mags) {
            apprenticesCounter += calculateApprentices(m.getApprentices());
            apprenticesCounter++;
        }
        return apprenticesCounter;
    }

    public static void main(String[] args) {
//        if(args.length == 0)
//        {
//            System.out.println("Brak argumentów wejściowych");
//            return;
//        }
        String sortType = "alt";
        Mage.sortType = sortType;

        Set<Mage> mags = SetFactory.createSet(sortType);
        mags.addAll(createTestData());
        MagePresenter magePresenter = new MagePresenter(mags);
        magePresenter.printMagsTree();

        for (Map.Entry<Mage, Integer> e : createMap(mags, sortType).entrySet()) {
            System.out.println(e.getKey().toString() + " | NUMBER: " + e.getValue());
        }
    }
}