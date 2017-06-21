package pl.witoldbrzezinski.footballscores.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.witoldbrzezinski.footballscores.LeagueClickListener;
import pl.witoldbrzezinski.footballscores.LeaguesItemView;
import pl.witoldbrzezinski.footballscores.MainActivity;
import pl.witoldbrzezinski.footballscores.R;



public class LeaguesFragment extends Fragment implements LeagueClickListener {

    @BindView(R.id.bundesliga_item)
    LeaguesItemView bundesligaItem;
    @BindView(R.id.laliga_item)
    LeaguesItemView laligaItem;
    @BindView(R.id.premierleague_item)
    LeaguesItemView premierleagueItem;
    @BindView(R.id.seriea_item)
    LeaguesItemView serieaItem;
    @BindView(R.id.league_toolbar)
    Toolbar leagueToolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.leagues_layout,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, getActivity());
        leagueToolbar.setTitle(R.string.app_name);
        setUpListeners();
    }

    private void setUpListeners() {
        bundesligaItem.setLeagueClickListener(this);
        laligaItem.setLeagueClickListener(this);
        premierleagueItem.setLeagueClickListener(this);
        serieaItem.setLeagueClickListener(this);
    }

    @Override
    public void onTableClick(String id,String name) {
        ((MainActivity)getActivity()).replaceToTableFragment(id,name);
    }

    @Override
    public void onFixturesClick(String id, String matchDays, String name) {
        ((MainActivity)getActivity()).replaceToFixturesFragment(id, matchDays,name);
    }
}
