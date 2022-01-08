package org.thepanday.informatikproject.common.entity.jsonentities;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.thepanday.informatikproject.common.entity.MatchStatEnum;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "h_a", "xG", "xGA", "npxG", "npxGA", "ppda", "ppda_allowed", "deep", "deep_allowed", "scored", "missed", "xpts", "result", "date", "wins",
                     "draws", "loses", "pts", "npxGD" })
public class MatchHistory {

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
    private final Map<MatchStatEnum, Object> mStatEnumToValue = new EnumMap<>(MatchStatEnum.class);

    public Object get(MatchStatEnum statEnum) {
        return mStatEnumToValue.get(statEnum);
    }

    @JsonProperty("h_a")
    public String getHA() {
        return hA;
    }

    @JsonProperty("h_a")
    public void setHA(String hA) {
        this.hA = hA;
        mStatEnumToValue.put(MatchStatEnum.HOME_AWAY, hA);
    }

    @JsonProperty("xG")
    public Double getXG() {
        return xG;
    }

    @JsonProperty("xG")
    public void setXG(Double xG) {
        this.xG = xG;
        mStatEnumToValue.put(MatchStatEnum.EXPECTED_GOALS, xG);
    }

    @JsonProperty("xGA")
    public Double getXGA() {
        return xGA;
    }

    @JsonProperty("xGA")
    public void setXGA(Double xGA) {
        this.xGA = xGA;
        mStatEnumToValue.put(MatchStatEnum.EXPECTED_GOALS_AGAINST, xGA);
    }

    @JsonProperty("npxG")
    public Double getNpxG() {
        return npxG;
    }

    @JsonProperty("npxG")
    public void setNpxG(Double npxG) {
        this.npxG = npxG;
        mStatEnumToValue.put(MatchStatEnum.NON_PENALTY_EXPECTED_GOALS, npxG);
    }

    @JsonProperty("npxGA")
    public Double getNpxGA() {
        return npxGA;
    }

    @JsonProperty("npxGA")
    public void setNpxGA(Double npxGA) {
        this.npxGA = npxGA;
        mStatEnumToValue.put(MatchStatEnum.NON_PENALTY_EXPECTED_GOALS_AGAINST, npxGA);
    }

    @JsonProperty("ppda")
    public Ppda getPpda() {
        return ppda;
    }

    @JsonProperty("ppda")
    public void setPpda(Ppda ppda) {
        this.ppda = ppda;
        mStatEnumToValue.put(MatchStatEnum.PPDA_ATT, ppda.getAtt());
        mStatEnumToValue.put(MatchStatEnum.PPDA_DEF, ppda.getDef());
    }

    @JsonProperty("ppda_allowed")
    public Ppda getPpdaAllowed() {
        return ppdaAllowed;
    }

    @JsonProperty("ppda_allowed")
    public void setPpdaAllowed(Ppda ppdaAllowed) {
        this.ppdaAllowed = ppdaAllowed;
        mStatEnumToValue.put(MatchStatEnum.PPDA_ALLOWED_ATT, ppdaAllowed.getAtt());
        mStatEnumToValue.put(MatchStatEnum.PPDA_ALLOWED_DEF, ppdaAllowed.getDef());
    }

    @JsonProperty("deep")
    public Integer getDeep() {
        return deep;
    }

    @JsonProperty("deep")
    public void setDeep(Integer deep) {
        this.deep = deep;
        mStatEnumToValue.put(MatchStatEnum.DEEP, deep);
    }

    @JsonProperty("deep_allowed")
    public Integer getDeepAllowed() {
        return deepAllowed;
    }

    @JsonProperty("deep_allowed")
    public void setDeepAllowed(Integer deepAllowed) {
        this.deepAllowed = deepAllowed;
        mStatEnumToValue.put(MatchStatEnum.DEEP_ALLOWED, deepAllowed);
    }

    @JsonProperty("scored")
    public Integer getScored() {
        return scored;
    }

    @JsonProperty("scored")
    public void setScored(Integer scored) {
        this.scored = scored;
        mStatEnumToValue.put(MatchStatEnum.GOALS_SCORED, scored);
    }

    @JsonProperty("missed")
    public Integer getMissed() {
        return missed;
    }

    @JsonProperty("missed")
    public void setMissed(Integer missed) {
        this.missed = missed;
        mStatEnumToValue.put(MatchStatEnum.GOALS_CONCEIVED, missed);
    }

    @JsonProperty("xpts")
    public Double getXpts() {
        return xpts;
    }

    @JsonProperty("xpts")
    public void setXpts(Double xpts) {
        this.xpts = xpts;
        mStatEnumToValue.put(MatchStatEnum.EXPECTED_POINTS, xpts);
    }

    @JsonProperty("result")
    public String getResult() {
        return result;
    }

    @JsonProperty("result")
    public void setResult(String result) {
        this.result = result;
        mStatEnumToValue.put(MatchStatEnum.RESULT, result);
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
        mStatEnumToValue.put(MatchStatEnum.DATE, date);
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
