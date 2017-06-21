package pl.witoldbrzezinski.footballscores.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.zakariya.stickyheaders.SectioningAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pl.witoldbrzezinski.footballscores.R;
import pl.witoldbrzezinski.footballscores.model.fixtures.Fixture;


public class StickyHeadersAdapter extends SectioningAdapter {

    List<Fixture> fixtureList;
    List<Section> sections = new ArrayList<>();
    int matchDate;
    String score = "";
    static final boolean USE_DEBUG_APPEARANCE = false;

    public StickyHeadersAdapter() {
    }

    private class Section {
        String matchDay;
        ArrayList<Fixture> fixtures = new ArrayList<>();
    }
    public void setFixtures(List<Fixture> fixtures) {
        this.fixtureList = fixtures;
        matchDate = 0;
        sections.clear();
        Section currentSection = null;
        for (Fixture fixture : fixtures){
            if(fixture.getMatchday()!=matchDate){
                if (currentSection!=null){
                    sections.add(currentSection);
                }
                currentSection = new Section();
                matchDate = fixture.getMatchday();
                currentSection.matchDay = String.valueOf(matchDate);
            }
            if(currentSection!=null){
                currentSection.fixtures.add(fixture);
            }
        }
        sections.add(currentSection);
        notifyAllSectionsDataSetChanged();
    }

    public class ItemViewHolder extends SectioningAdapter.ItemViewHolder {

        Context mContext;
        TextView homeNameTextView;
        TextView awayNameTextView;
        TextView dateTextView;
        TextView scoreTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            homeNameTextView =  itemView.findViewById(R.id.fixture_home_name);
            awayNameTextView = itemView.findViewById(R.id.fixture_away_name);
            dateTextView = itemView.findViewById(R.id.fixture_date_textview);
            scoreTextView = itemView.findViewById(R.id.fixture_score_textview);
            mContext = itemView.getContext();
        }
    }

    public class HeaderViewHolder extends SectioningAdapter.HeaderViewHolder {

        Context mContext;
        TextView headerTextView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            headerTextView = itemView.findViewById(R.id.header_fixtures);
            mContext = itemView.getContext();
        }
    }

    @Override
    public int getNumberOfSections() {
        return sections.size();
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return sections.get(sectionIndex).fixtures.size();
    }


    @Override
    public boolean doesSectionHaveHeader(int sectionIndex) {
        return true;
    }

    @Override
    public boolean doesSectionHaveFooter(int sectionIndex) {
        return false;
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.fixture_item_layout,parent,false);
        return new ItemViewHolder(v);
    }


    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.fixture_header_item, parent, false);
        return new HeaderViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, int sectionIndex, int itemIndex, int itemType) {
        Section s = sections.get(sectionIndex);
        Fixture fixture = s.fixtures.get(itemIndex);
        ((ItemViewHolder) viewHolder).homeNameTextView.setText(fixture.getHomeTeamName());
        ((ItemViewHolder) viewHolder).awayNameTextView.setText(fixture.getAwayTeamName());
        ((ItemViewHolder) viewHolder).dateTextView.setText(fixture.getDate().substring(0,10)+" "+fixture.getDate().substring(11,16));
        matchDate = fixture.getMatchday();
        if (fixture.getResult().getGoalsAwayTeam() != null) {
            score = fixture.getResult().getGoalsHomeTeam().toString()+":"+fixture.getResult().getGoalsAwayTeam().toString();
            ((ItemViewHolder) viewHolder).scoreTextView.setText(score);
        }else {
            ((ItemViewHolder) viewHolder).scoreTextView.setText(R.string.tbd);
        }
    }


    public String setLanguage(){
        String language = Locale.getDefault().getLanguage();
        if (language.equals("pl")){
            return "Kolejka: ";
        }else return "Matchdate: ";
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder, int sectionIndex, int headerType) {
        Section s = sections.get(sectionIndex);
        HeaderViewHolder hvh = (HeaderViewHolder) viewHolder;
        String language = Locale.getDefault().getDisplayLanguage();

        if (USE_DEBUG_APPEARANCE) {
            hvh.itemView.setBackgroundColor(0x55ffffff);
            ((HeaderViewHolder)viewHolder).headerTextView.setText("");
        } else {
            ((HeaderViewHolder)viewHolder).headerTextView.setText(setLanguage()+ Integer.toString(sectionIndex+1));
            ((HeaderViewHolder)viewHolder).headerTextView.setTextColor(0xcc767676);
        }
    }

}
