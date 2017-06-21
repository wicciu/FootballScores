
package pl.witoldbrzezinski.footballscores.model.tables;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LinksToTeams {

    @SerializedName("team")
    @Expose
    private Team team;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

}
