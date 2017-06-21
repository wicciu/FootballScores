package pl.witoldbrzezinski.footballscores.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import pl.witoldbrzezinski.footballscores.LeaguesItemView;
import pl.witoldbrzezinski.footballscores.R;
import pl.witoldbrzezinski.footballscores.adapters.CompetitionsAdapter;
import pl.witoldbrzezinski.footballscores.adapters.TableAdapter;
import pl.witoldbrzezinski.footballscores.model.tables.Standing;
import pl.witoldbrzezinski.footballscores.model.tables.Table;
import pl.witoldbrzezinski.footballscores.rest.ApiClient;



public class TableFragment extends Fragment {

    @BindView(R.id.table_recycler_view)
    RecyclerView tableRecyclerView;
    @BindView(R.id.table_toolbar)
    Toolbar tableToolbar;

    private Context mContext = null;
    private RecyclerView.LayoutManager mLayoutManager =null;
    private RecyclerView.Adapter tableAdapter =null;
    private ArrayList<Table> tableArrayList;
    private LeaguesItemView leaguesItemView = null;
    private ProgressDialog progressDialog = null;

    @Nullable
    @Override
    //DO NOT WRITE ANY EXTRA CODE TO THIS METHOD, DO EVERYTHING IN onViewCreated
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.table_layout,container,false);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, getActivity());
        tableToolbar.setTitle(getLeagueNameFromBundle());
        setRecyclerViewData();
        getTableFromAPI();
        progressDialog = createProgressDialog();
        progressDialog.show();
    }

    private void setRecyclerViewData(){
        mContext =getActivity().getApplicationContext();
        leaguesItemView = new LeaguesItemView(mContext);
        mLayoutManager = new LinearLayoutManager(mContext);
        tableRecyclerView.setLayoutManager(mLayoutManager);
        tableAdapter= new CompetitionsAdapter(Collections.EMPTY_LIST);
        tableRecyclerView.setAdapter(tableAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL);
        tableRecyclerView.addItemDecoration(itemDecoration);
    }

    private void getTableFromAPI() {
        String id = getLeagueIdFromBundle();
        Observable<List<Standing>> standingObservable = ApiClient.getStandingList(id);
        standingObservable.subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Standing>>() {
            @Override
            public void onNext(List<Standing> value) {
                setList(value);
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getActivity().getApplicationContext(),R.string.something_went_wrong,Toast.LENGTH_LONG);
            }

            @Override
            public void onComplete() {
                progressDialog.hide();
            }
        });
    }

    public void setList(List<Standing> list){
        tableArrayList = new ArrayList<>();
        tableAdapter = new TableAdapter(list);
        tableRecyclerView.setAdapter(tableAdapter);
    }

    public String getLeagueIdFromBundle(){
        String id = getArguments().getString("id");
        return id;
    }

    public String getLeagueNameFromBundle(){
        String name = getArguments().getString("leagueName");
        return name;
    }

    public ProgressDialog createProgressDialog() {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle(R.string.connecting);
        dialog.setMessage(TableFragment.this.getString(R.string.wait));
        dialog.setCancelable(true);
        return dialog;
    }


}
