package org.thepanday.informatikproject.common.entity.jsonentities;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.thepanday.informatikproject.util.MatchHistoryUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * All details of a team. Includes team id, title and list of matches played by the team.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "title", "history" })
public class TeamMatchesDetail {

    @JsonProperty("id")
    private String id;
    @JsonProperty("title")
    private String teamName;
    @JsonProperty("history")
    private List<MatchHistory> mMatchHistory = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    private List<MatchHistory> mHomeGames = new ArrayList<>();
    private List<MatchHistory> mAwayGames = new ArrayList<>();

    public MatchHistory getAverageMatchHistory() {
        return MatchHistoryUtility.getAverageMatchHistory(mMatchHistory);
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("title")
    public String getTeamName() {
        return teamName;
    }

    @JsonProperty("title")
    public void setTeamName(String title) {
        this.teamName = title;
    }

    @JsonProperty("history")
    public List<MatchHistory> getHistory() {
        return mMatchHistory;
    }

    @JsonProperty("history")
    public void setHistory(List<MatchHistory> matchHistory) {
        this.mMatchHistory = matchHistory;
        for (MatchHistory singleMatchHistory : matchHistory) {
            if ("h".equals(singleMatchHistory.getHA())) {
                mHomeGames.add(singleMatchHistory);
            } else {
                mAwayGames.add(singleMatchHistory);
            }
        }

    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
