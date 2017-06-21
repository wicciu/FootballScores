package pl.witoldbrzezinski.footballscores.rest;

import android.util.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.witoldbrzezinski.footballscores.model.fixtures.Fixture;
import pl.witoldbrzezinski.footballscores.model.fixtures.FixturesResponse;
import pl.witoldbrzezinski.footballscores.model.leagues.League;
import pl.witoldbrzezinski.footballscores.model.tables.Standing;
import pl.witoldbrzezinski.footballscores.model.tables.Table;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    public static final String BASE_URL = "https://api.football-data.org/v1/";
    public static final String API_KEY = "0ed7ba1c82ab4d52bb49524a272a0aad";
    private static RetrofitInterface retrofitInterface;

    private static Retrofit retrofit = null;

    private static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().
                    addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                    client(getHeader(API_KEY)).
                    addConverterFactory(GsonConverterFactory.create()).
                    baseUrl(BASE_URL).
                    build();
        }
        return retrofit;
    }

    private static RetrofitInterface getRetrofitInterface() {
        if (retrofitInterface == null) {
            retrofitInterface = getClient().create(RetrofitInterface.class);
        }
        return retrofitInterface;
    }

    private static OkHttpClient getHeader(final String authorizationValue) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Interceptor.Chain chain) throws IOException {
                                Request request = null;
                                if (authorizationValue != null) {
                                    Log.d("--Authorization-- ", authorizationValue);

                                    Request original = chain.request();
                                    // Request customization: add request headers
                                    Request.Builder requestBuilder = original.newBuilder()
                                            .addHeader("Authorization", authorizationValue);

                                    request = requestBuilder.build();
                                }
                                return chain.proceed(request);
                            }
                        })
                .build();
        return okClient;
    }

    public static Observable<List<Standing>> getStandingList(String id) {
        return getRetrofitInterface().getLeagueTable(id).map(new Function<Table, List<Standing>>() {
            @Override
            public List<Standing> apply(Table table) throws Exception {
                return table.getStanding();
            }
        }).compose(ApiClient.<List<Standing>>applySchedulers());
    }

    public static Observable<List<League>> getLeaguesWithRx() {
        return getRetrofitInterface().getLeaguesWithRx().compose(ApiClient.<List<League>>applySchedulers());
    }


    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static Observable<List<Fixture>> getFixtureList(String id) {
        return getRetrofitInterface().getFixtures(id).map(new Function<FixturesResponse, List<Fixture>>() {
            @Override
            public List<Fixture> apply(FixturesResponse fixturesResponse) throws Exception {
                List<Fixture> sortedList = fixturesResponse.getFixtures();
                Collections.sort(sortedList,new FixturesComparator.MatchDateComparator());
                return sortedList;
            }
        }).compose(ApiClient.<List<Fixture>>applySchedulers());
    }

}
