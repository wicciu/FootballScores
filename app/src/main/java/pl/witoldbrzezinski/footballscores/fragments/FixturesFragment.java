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

import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import pl.witoldbrzezinski.footballscores.R;
import pl.witoldbrzezinski.footballscores.adapters.StickyHeadersAdapter;
import pl.witoldbrzezinski.footballscores.model.fixtures.Fixture;
import pl.witoldbrzezinski.footballscores.rest.ApiClient;


public class FixturesFragment extends Fragment {


    @BindView(R.id.fixture_recycler_view)
    RecyclerView fixtureRecyclerView;
    @BindView(R.id.fixture_toolbar)
    Toolbar fixtureToolbar;

    private Context mContext = null;
    private StickyHeadersAdapter stickyHeadersAdapter = new StickyHeadersAdapter();
    private ArrayList<Fixture> fixturesArrayList;
    private ProgressDialog progressDialog = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fixtures_layout,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, getActivity());
        fixtureToolbar.setTitle(getLeagueNameFromBundle());
        setRecyclerViewData();
        getFixturesFromAPI();
        progressDialog = createProgressDialog();
        progressDialog.show();
    }

    private void setRecyclerViewData(){
        mContext =getActivity().getApplicationContext();
        fixtureRecyclerView.setLayoutManager(new StickyHeaderLayoutManager());
        stickyHeadersAdapter = new StickyHeadersAdapter();
        fixtureRecyclerView.setAdapter(stickyHeadersAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL);
        fixtureRecyclerView.addItemDecoration(itemDecoration);
    }

    private void getFixturesFromAPI(){
        String id = getLeagueIdFromBundleFixtures();
        Observable<List<Fixture>> fixtureObservable = ApiClient.getFixtureList(id);
        fixtureObservable.subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Fixture>>() {
            @Override
            public void onNext(List<Fixture> value) {
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

    public String getLeagueIdFromBundleFixtures(){
        String id = getArguments().getString("id");
        return id;
    }

    public String getLeagueNameFromBundle(){
        String name = getArguments().getString("leagueName");
        return name;
    }

    public void setList(List<Fixture> list){
        fixturesArrayList = new ArrayList<>();
        stickyHeadersAdapter = new StickyHeadersAdapter();
        fixtureRecyclerView.setAdapter(stickyHeadersAdapter);
        stickyHeadersAdapter.setFixtures(list);
    }

    public ProgressDialog createProgressDialog() {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle(R.string.connecting);
        dialog.setMessage(FixturesFragment.this.getString(R.string.wait));
        dialog.setCancelable(true);
        return dialog;
    }
}
