package pl.witoldbrzezinski.footballscores.rest;

import java.util.List;

import io.reactivex.Observable;
import pl.witoldbrzezinski.footballscores.model.fixtures.FixturesResponse;
import pl.witoldbrzezinski.footballscores.model.leagues.League;
import pl.witoldbrzezinski.footballscores.model.tables.Table;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

import static pl.witoldbrzezinski.footballscores.rest.ApiClient.API_KEY;

/**
 * Created by Wiciu on 21.06.2017.
 */

public interface RetrofitInterface {

    @GET("competitions")
    @Headers("X-Auth-Token:" +API_KEY)
    Observable<List<League>> getLeaguesWithRx();

    @GET("competitions/{id}/leagueTable")
    @Headers("X-Auth-Token:" +API_KEY)
    Observable<Table> getLeagueTable(@Path("id") String id);

    @GET("competitions/{id}/fixtures")
    @Headers("X-Auth-Token:" +API_KEY)
    Observable<FixturesResponse> getFixtures(@Path("id") String id);

}
