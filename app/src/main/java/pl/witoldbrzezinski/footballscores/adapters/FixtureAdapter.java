package pl.witoldbrzezinski.footballscores.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.witoldbrzezinski.footballscores.R;
import pl.witoldbrzezinski.footballscores.model.fixtures.Fixture;


public class FixtureAdapter extends RecyclerView.Adapter<FixtureAdapter.FixtureHolder> {

    private List<Fixture> mFixtureList;
    private Context mContext;
    String score = "";
    int matchDate;

    public FixtureAdapter(List<Fixture> mFixtureList) {
        this.mFixtureList = mFixtureList;
    }

    @Override
    public FixtureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fixture_item_layout,parent,false);
        FixtureAdapter.FixtureHolder fixtureHolder = new FixtureAdapter.FixtureHolder(view);
        return fixtureHolder;
    }

    @Override
    public void onBindViewHolder(FixtureHolder holder, int position) {
        mContext = holder.itemView.getContext();
        Fixture fixture = mFixtureList.get(position);
        holder.homeNameTextView.setText(fixture.getHomeTeamName());
        holder.awayNameTextView.setText(fixture.getAwayTeamName());
        holder.dateTextView.setText(fixture.getDate());
        matchDate = fixture.getMatchday();
        if (fixture.getResult().getGoalsAwayTeam() != null) {
            score = fixture.getResult().getGoalsHomeTeam().toString()+":"+fixture.getResult().getGoalsAwayTeam().toString();
            holder.scoreTextView.setText(score);
        }else {
            holder.scoreTextView.setText(R.string.tbd);
        }
    }

    @Override
    public int getItemCount() {
        return mFixtureList.size();
    }

    public class FixtureHolder extends RecyclerView.ViewHolder {

        Context mContext;
        @BindView(R.id.fixture_home_name)TextView homeNameTextView;
        @BindView(R.id.fixture_away_name)TextView awayNameTextView;
        @BindView(R.id.fixture_date_textview)TextView dateTextView;
        @BindView(R.id.fixture_score_textview)TextView scoreTextView;


        public FixtureHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }
    }
}
