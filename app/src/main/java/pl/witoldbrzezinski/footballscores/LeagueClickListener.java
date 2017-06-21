package pl.witoldbrzezinski.footballscores;



public interface LeagueClickListener {

    void onTableClick(String id, String name);

    void onFixturesClick(String id, String matchDays, String name);
}
