////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 26.01.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.common.entity.jsonentities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
// todo: Look at what Neuroph wants as trainging data for structure ideas.
public class TrainingData {
    @JsonProperty("input")
    public List<Double> input = new ArrayList<>();
    @JsonProperty("output")
    public List<Integer> output = new ArrayList<>();

    @JsonProperty("input")
    public List<Double> getInput() {
        return input;
    }

    @JsonProperty("input")
    public void setInput(List<Double> input) {
        this.input = input;
    }

    @JsonProperty("output")
    public List<Integer> getOutput() {
        return output;
    }

    @JsonProperty("output")
    public void setOutput(List<Integer> output) {
        this.output = output;
    }

    public TrainingData addInput(double stat) {
        input.add(stat);
        return this;
    }

    /**
     * Add inputs in order. HA, XG, Deep, xGA, Deepallowed
     * @param stat
     * @return
     */
    public TrainingData addInput(double... stat) {
        Arrays.stream(stat).forEach(this::addInput);
        return this;
    }

    public void addOutput(int scored, int concieved) {
        output.add(scored);
        output.add(concieved);
    }
}
