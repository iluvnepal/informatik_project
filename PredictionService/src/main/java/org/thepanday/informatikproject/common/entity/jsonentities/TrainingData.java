////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 26.01.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.common.entity.jsonentities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class TrainingData {

    private List<Double> mInput = new ArrayList<>();
    private final AtomicInteger mInputCounts = new AtomicInteger(0);

    public List<Double> getInput() {
        return mInput;
    }

    public void setInput(List<Double> input) {
        this.mInput = input;
        mInputCounts.incrementAndGet();
    }

    public AtomicInteger getInputCounts() {
        return mInputCounts;
    }

    /**
     * Performs rudimentary check for training data consistency by comparing counts of input and output.
     *
     * @param dataA
     * @param dataB
     * @return {@code true} if consistent, else {@code false}
     */
    public static boolean isDataConsistent(TrainingData dataA, TrainingData dataB) {
        return (dataA
                    .getInputCounts()
                    .get() == dataB
                    .getInputCounts()
                    .get());
    }

    public TrainingData addInput(double stat) {
        mInput.add(stat);
        return this;
    }

    /**
     * Add inputs in order. HA, XG, Deep, xGA, Deepallowed
     *
     * @param stat
     * @return
     */

    public TrainingData addInput(double... stat) {
        Arrays
            .stream(stat)
            .forEach(this::addInput);
        return this;
    }
}
