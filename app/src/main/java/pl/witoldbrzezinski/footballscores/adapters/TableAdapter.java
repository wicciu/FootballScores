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
import pl.witoldbrzezinski.footballscores.model.tables.Standing;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableHolder> {

    private List<Standing> mStandingList;
    private Context mContext;

    public TableAdapter(List<Standing> mStandingList) {
        this.mStandingList = mStandingList;
    }

    @Override
    public TableHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_item_layout,parent,false);
        TableHolder tableHolder = new TableHolder(view);
        return tableHolder;
    }

    @Override
    public void onBindViewHolder(TableHolder holder, int position) {
        mContext = holder.itemView.getContext();
        Standing standing = mStandingList.get(position);
        holder.teamNameTextView.setText(standing.getTeamName());
        holder.teamPositionTextView.setText(standing.getPosition().toString());
        holder.teamPlayedTextView.setText(standing.getPlayedGames().toString());
        holder.teamWonTextView.setText(standing.getWins().toString());
        holder.teamDrawnTextView.setText(standing.getDraws().toString());
        holder.teamLostTextView.setText(standing.getLosses().toString());
        holder.teamForTextView.setText(standing.getGoals().toString());
        holder.teamAgainstTextView.setText(standing.getGoalsAgainst().toString());
        holder.teamPointsTextView.setText(standing.getPoints().toString());
    }

    @Override
    public int getItemCount() {
        return mStandingList.size();
    }

    public static class TableHolder extends RecyclerView.ViewHolder {

        Context mContext;
        @BindView(R.id.team_name)
        TextView teamNameTextView;
        @BindView(R.id.team_position) TextView teamPositionTextView;
        @BindView(R.id.team_played) TextView teamPlayedTextView;
        @BindView(R.id.team_won) TextView teamWonTextView;
        @BindView(R.id.team_drawn) TextView teamDrawnTextView;
        @BindView(R.id.team_lost) TextView teamLostTextView;
        @BindView(R.id.team_for) TextView teamForTextView;
        @BindView(R.id.team_against) TextView teamAgainstTextView;
        @BindView(R.id.team_points) TextView teamPointsTextView;


        public TableHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

    }
}
