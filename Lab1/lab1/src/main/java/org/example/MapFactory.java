package org.example;

import java.util.*;

public class MapFactory {
    static final String NO_SORTING = "none";
    static final String DEFAULT_SORT = "sort";
    static final String ALTERNATIVE_SORT = "alt";

    public static Map<Mage, Integer> createMap(String sortingType) {
        Map<Mage, Integer> mags;
        if (sortingType.equals(DEFAULT_SORT)) {
            mags = new TreeMap<>();
        } else if (sortingType.equals(ALTERNATIVE_SORT)) {
            MageCompare mageCompare = new MageCompare(new ArrayList<>(List.of(
                    MageCompare.MageField.Level,
                    MageCompare.MageField.Name,
                    MageCompare.MageField.Power
            )));
            mags = new TreeMap<>(mageCompare);
        } else {
            mags = new HashMap<>();
        }

        return mags;
    }

}
