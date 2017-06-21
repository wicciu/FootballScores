package pl.witoldbrzezinski.footballscores;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


import pl.witoldbrzezinski.footballscores.fragments.FixturesFragment;
import pl.witoldbrzezinski.footballscores.fragments.LeaguesFragment;
import pl.witoldbrzezinski.footballscores.fragments.TableFragment;

public class MainActivity extends AppCompatActivity {

    private LeaguesFragment leaguesFragment = new LeaguesFragment();
    private TableFragment tableFragment = new TableFragment();
    private FixturesFragment fixturesFragment = new FixturesFragment();
    private LeaguesItemView leaguesItemView;
    public static final String LEAGUES_TAG = "LEAGUES_TAG";
    public static final String TABLE_TAG = "TABLE_TAG";
    public static final String FIXTURES_TAG = "FIXTURES_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        leaguesItemView = new LeaguesItemView(getApplicationContext());
        replaceToLeaguesFragment();
    }

    public void replaceToLeaguesFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,leaguesFragment,LEAGUES_TAG);
        fragmentTransaction.addToBackStack(leaguesFragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }
    public void replaceToTableFragment(String id, String name){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        String leagueId  = leaguesItemView.getLeagueId();
        String leagueName = leaguesItemView.getLeagueName();
        leagueName = name;
        leagueId = id;
        getParametersFromBundle(id,"",leagueName);
        fragmentTransaction.replace(R.id.main_frame, tableFragment, TABLE_TAG);
        fragmentTransaction.addToBackStack(tableFragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    public void replaceToFixturesFragment(String id, String matchdays, String name){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        String leagueId  = leaguesItemView.getLeagueId();
        String leagueMatchdays = leaguesItemView.getMatchDays();
        String leagueName = leaguesItemView.getLeagueName();
        leagueId = id;
        leagueMatchdays = matchdays;
        leagueName = name;
        getParametersFromBundle(id,matchdays,name);
        fragmentTransaction.replace(R.id.main_frame, fixturesFragment, FIXTURES_TAG);
        fragmentTransaction.addToBackStack(fixturesFragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    public void getParametersFromBundle(String id, String matchdays,String leagueName){
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        bundle.putString("matchdays",matchdays);
        bundle.putString("leagueName",leagueName);
        tableFragment.setArguments(bundle);
        fixturesFragment.setArguments(bundle);
    }
    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(LEAGUES_TAG);
        if (fragment.isVisible()){
            finish();
        }else {
            super.onBackPressed();
        }
    }


}
