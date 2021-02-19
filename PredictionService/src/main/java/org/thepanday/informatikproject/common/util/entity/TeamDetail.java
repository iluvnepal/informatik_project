
package org.thepanday.informatikproject.common.util.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * All details of a team. Includes team id, title and list of matches played by the team.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "title",
    "history"
})
public class TeamDetail {

    @JsonProperty("id")
    private String id;
    @JsonProperty("title")
    private String teamName;
    @JsonProperty("history")
    private List<History> history = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

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
    public List<History> getHistory() {
        return history;
    }

    @JsonProperty("history")
    public void setHistory(List<History> history) {
        this.history = history;
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
