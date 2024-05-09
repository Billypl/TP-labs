package org.example;

import java.util.*;

public class SetFactory {

    static final String NO_SORTING = "none";
    static final String DEFAULT_SORT = "sort";
    static final String ALTERNATIVE_SORT = "alt";

    public static Set<Mage> createSet(String sortingType) {
        Set<Mage> mags;
        if (sortingType.equals(DEFAULT_SORT)) {
            mags = new TreeSet<>();
        } else if (sortingType.equals(ALTERNATIVE_SORT)) {
            MageCompare mageCompare = new MageCompare(new ArrayList<>(List.of(
                    MageCompare.MageField.Level,
                    MageCompare.MageField.Name,
                    MageCompare.MageField.Power
            )));
            mags = new TreeSet<>(mageCompare);
        } else {
            mags = new HashSet<>();
        }

        return mags;
    }
}
