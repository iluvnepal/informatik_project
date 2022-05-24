////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 06.01.2022.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.common.data;

import org.neuroph.core.data.DataSet;
import org.thepanday.informatikproject.common.entity.jsonentities.TrainingData;

import java.util.List;

public class TrainingDataSet extends DataSet {

    private final List<TrainingData> mTrainingDataList;

    public TrainingDataSet(List<TrainingData> trainingDataList, int inputSize, int outputSize) {
        super(inputSize, outputSize);
        mTrainingDataList = trainingDataList;
        init();
    }

    private void init() {
        mTrainingDataList.forEach(trainingData -> {
            final double[] input = trainingData
                .getInput()
                .stream()
                .mapToDouble(Double::doubleValue)
                .toArray();
            final double[] output = trainingData
                .getOutput()
                .stream()
                .mapToDouble(Double::doubleValue)
                .toArray();
            super.add(input, output);
        });
    }
}
