package org.thepanday.informatikproject.common.entity.jsonentities;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "att", "def" })
public class Ppda {

    @JsonProperty("att")
    private Integer att;
    @JsonProperty("def")
    private Integer def;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    public double getPpda() {
        return def == 0 ? 0 : ((double) att / def);
    }

    @JsonProperty("att")
    public Integer getAtt() {
        return att;
    }

    @JsonProperty("att")
    public void setAtt(Integer att) {
        this.att = att;
    }

    @JsonProperty("def")
    public Integer getDef() {
        return def;
    }

    @JsonProperty("def")
    public void setDef(Integer def) {
        this.def = def;
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
