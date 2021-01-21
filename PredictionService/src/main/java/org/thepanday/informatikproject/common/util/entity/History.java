package org.thepanday.informatikproject.common.util.entity;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "h_a", "xG", "xGA", "npxG", "npxGA", "ppda", "ppda_allowed", "deep", "deep_allowed", "scored", "missed", "xpts", "result", "date", "wins",
                     "draws", "loses", "pts", "npxGD" })
public class History {

    @JsonProperty("h_a")
    private String hA;
    @JsonProperty("xG")
    private Double xG;
    @JsonProperty("xGA")
    private Double xGA;
    @JsonProperty("npxG")
    private Double npxG;
    @JsonProperty("npxGA")
    private Double npxGA;
    @JsonProperty("ppda")
    private Ppda ppda;
    @JsonProperty("ppda_allowed")
    private Ppda ppdaAllowed;
    @JsonProperty("deep")
    private Integer deep;
    @JsonProperty("deep_allowed")
    private Integer deepAllowed;
    @JsonProperty("scored")
    private Integer scored;
    @JsonProperty("missed")
    private Integer missed;
    @JsonProperty("xpts")
    private Double xpts;
    @JsonProperty("result")
    private String result;
    @JsonProperty("date")
    private String date;
    @JsonProperty("wins")
    private Integer wins;
    @JsonProperty("draws")
    private Integer draws;
    @JsonProperty("loses")
    private Integer loses;
    @JsonProperty("pts")
    private Integer pts;
    @JsonProperty("npxGD")
    private Double npxGD;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("h_a")
    public String getHA() {
        return hA;
    }

    @JsonProperty("h_a")
    public void setHA(String hA) {
        this.hA = hA;
    }

    @JsonProperty("xG")
    public Double getXG() {
        return xG;
    }

    @JsonProperty("xG")
    public void setXG(Double xG) {
        this.xG = xG;
    }

    @JsonProperty("xGA")
    public Double getXGA() {
        return xGA;
    }

    @JsonProperty("xGA")
    public void setXGA(Double xGA) {
        this.xGA = xGA;
    }

    @JsonProperty("npxG")
    public Double getNpxG() {
        return npxG;
    }

    @JsonProperty("npxG")
    public void setNpxG(Double npxG) {
        this.npxG = npxG;
    }

    @JsonProperty("npxGA")
    public Double getNpxGA() {
        return npxGA;
    }

    @JsonProperty("npxGA")
    public void setNpxGA(Double npxGA) {
        this.npxGA = npxGA;
    }

    @JsonProperty("ppda")
    public Ppda getPpda() {
        return ppda;
    }

    @JsonProperty("ppda")
    public void setPpda(Ppda ppda) {
        this.ppda = ppda;
    }

    @JsonProperty("ppda_allowed")
    public Ppda getPpdaAllowed() {
        return ppdaAllowed;
    }

    @JsonProperty("ppda_allowed")
    public void setPpdaAllowed(Ppda ppdaAllowed) {
        this.ppdaAllowed = ppdaAllowed;
    }

    @JsonProperty("deep")
    public Integer getDeep() {
        return deep;
    }

    @JsonProperty("deep")
    public void setDeep(Integer deep) {
        this.deep = deep;
    }

    @JsonProperty("deep_allowed")
    public Integer getDeepAllowed() {
        return deepAllowed;
    }

    @JsonProperty("deep_allowed")
    public void setDeepAllowed(Integer deepAllowed) {
        this.deepAllowed = deepAllowed;
    }

    @JsonProperty("scored")
    public Integer getScored() {
        return scored;
    }

    @JsonProperty("scored")
    public void setScored(Integer scored) {
        this.scored = scored;
    }

    @JsonProperty("missed")
    public Integer getMissed() {
        return missed;
    }

    @JsonProperty("missed")
    public void setMissed(Integer missed) {
        this.missed = missed;
    }

    @JsonProperty("xpts")
    public Double getXpts() {
        return xpts;
    }

    @JsonProperty("xpts")
    public void setXpts(Double xpts) {
        this.xpts = xpts;
    }

    @JsonProperty("result")
    public String getResult() {
        return result;
    }

    @JsonProperty("result")
    public void setResult(String result) {
        this.result = result;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    @JsonProperty("wins")
    public Integer getWins() {
        return wins;
    }

    @JsonProperty("wins")
    public void setWins(Integer wins) {
        this.wins = wins;
    }

    @JsonProperty("draws")
    public Integer getDraws() {
        return draws;
    }

    @JsonProperty("draws")
    public void setDraws(Integer draws) {
        this.draws = draws;
    }

    @JsonProperty("loses")
    public Integer getLoses() {
        return loses;
    }

    @JsonProperty("loses")
    public void setLoses(Integer loses) {
        this.loses = loses;
    }

    @JsonProperty("pts")
    public Integer getPts() {
        return pts;
    }

    @JsonProperty("pts")
    public void setPts(Integer pts) {
        this.pts = pts;
    }

    @JsonProperty("npxGD")
    public Double getNpxGD() {
        return npxGD;
    }

    @JsonProperty("npxGD")
    public void setNpxGD(Double npxGD) {
        this.npxGD = npxGD;
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
