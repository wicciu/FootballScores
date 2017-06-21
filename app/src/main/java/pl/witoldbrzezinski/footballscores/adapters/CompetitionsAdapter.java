package pl.witoldbrzezinski.footballscores.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pl.witoldbrzezinski.footballscores.R;
import pl.witoldbrzezinski.footballscores.model.leagues.League;


public class CompetitionsAdapter extends RecyclerView.Adapter<CompetitionsAdapter.CompetitionsHolder> {

    private List<League> linksList;
    private Context mContext;

    public CompetitionsAdapter(List<League> linksList) {
        this.linksList = linksList;
    }

    @Override
    public CompetitionsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.competitions_item,parent,false);
        CompetitionsHolder competitionsHolder = new CompetitionsHolder(view);
        return competitionsHolder;
    }

    @Override
    public void onBindViewHolder(CompetitionsHolder holder, int position) {
        mContext = holder.itemView.getContext();
        League league = linksList.get(position);
        holder.caption = league.getCaption();
        holder.textView.setText(holder.caption);
    }

    @Override
    public int getItemCount() {
        return linksList.size();
    }

    public class CompetitionsHolder extends RecyclerView.ViewHolder {

        TextView textView;
        String caption = "";
        Context mContext;

        public CompetitionsHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            textView = (TextView)itemView.findViewById(R.id.event_venue);
        }

    }
}
