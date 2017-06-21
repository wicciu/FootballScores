
package pl.witoldbrzezinski.footballscores.model.fixtures;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Odds {

    @SerializedName("homeWin")
    @Expose
    private Double homeWin;
    @SerializedName("draw")
    @Expose
    private Double draw;
    @SerializedName("awayWin")
    @Expose
    private Double awayWin;

    public Double getHomeWin() {
        return homeWin;
    }

    public void setHomeWin(Double homeWin) {
        this.homeWin = homeWin;
    }

    public Double getDraw() {
        return draw;
    }

    public void setDraw(Double draw) {
        this.draw = draw;
    }

    public Double getAwayWin() {
        return awayWin;
    }

    public void setAwayWin(Double awayWin) {
        this.awayWin = awayWin;
    }

}
