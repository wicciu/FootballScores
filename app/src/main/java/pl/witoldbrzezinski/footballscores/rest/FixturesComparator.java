package pl.witoldbrzezinski.footballscores.rest;

import java.util.Comparator;

import pl.witoldbrzezinski.footballscores.model.fixtures.Fixture;

/**
 * Created by Wiciu on 21.06.2017.
 */

public class FixturesComparator {

    public static class MatchDateComparator implements Comparator<Fixture> {

        @Override
        public int compare(Fixture o1, Fixture o2) {
            return o1.getMatchday()-o2.getMatchday();
        }
    }
}
