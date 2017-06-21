package pl.witoldbrzezinski.footballscores;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class LeaguesItemView extends LinearLayout {

    private LeagueClickListener leagueClickListener;
    private RelativeLayout mRelativeLayout;
    private TextView leagueNameTextView;
    private String leagueId;
    private String leagueName;
    private String matchDays;
    private Button tableButton;
    private Button fixturesButton;
    ImageView countryImage;

    AttributeSet mAttrs;
    Context mContext;


    public LeaguesItemView(Context context) {
        super(context);
    }

    public LeaguesItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mAttrs =attrs;
        mContext = context;
        attachView(R.layout.leagues_item_layout);
        bindViews();
        collectAttrs();
        setListeners();
    }

    private void attachView(@LayoutRes int resId) {
        ViewGroup mContent = new LinearLayout(mContext, mAttrs);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        inflater.inflate(resId, mContent);
        addView(mContent);
    }

    private void bindViews(){
        mRelativeLayout = (RelativeLayout) findViewById(R.id.league_relative_layout);
        leagueNameTextView = (TextView)findViewById(R.id.league_textView);
        leagueId = getTag().toString();
        tableButton = (Button)findViewById(R.id.table_button);
        fixturesButton = (Button)findViewById(R.id.fixtures_button);
        countryImage = (ImageView)findViewById(R.id.flag);
    }

    private void collectAttrs(){
        TypedArray typedArray = mContext.obtainStyledAttributes(mAttrs,R.styleable.LeagueItemView);
        int i = typedArray.getResourceId(R.styleable.LeagueItemView_backgroundImage,-1);
        leagueName = typedArray.getString(R.styleable.LeagueItemView_leagueName);
        leagueId = typedArray.getString(R.styleable.LeagueItemView_leagueId);
        matchDays = typedArray.getString(R.styleable.LeagueItemView_matchDays);
        leagueNameTextView.setText(leagueName);
        countryImage.setImageResource(i);
    }

    private void setListeners(){

        tableButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leagueClickListener==null) {
                    return;
                }
                leagueClickListener.onTableClick(leagueId,leagueName);
            }
        });

        fixturesButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leagueClickListener == null) {
                    return;
                }
                leagueClickListener.onFixturesClick(leagueId, matchDays,leagueName);
            }
        });

    }

    public void setLeagueClickListener(LeagueClickListener leagueClickListener) {
        this.leagueClickListener = leagueClickListener;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public String getMatchDays() {
        return matchDays;
    }

    public String getLeagueName() {
        return leagueName;
    }


}
