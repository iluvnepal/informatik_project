////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 06.01.2022.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.common.data;

import deepnetts.data.DataSets;
import org.thepanday.informatikproject.common.entity.jsonentities.TrainingData;

import javax.visrec.ml.data.DataSet;
import java.io.IOException;
import java.util.List;

public class TrainingDataSet {

    private final List<TrainingData> mTrainingDataList;
    private final int mNumInputs;
    private final int mNumOutputs;

    public DataSet datasetFromCsv(String fileName) throws IOException {
        return DataSets.readCsv(fileName, mNumInputs, mNumOutputs, true);
    }

    public TrainingDataSet(List<TrainingData> trainingDataList, int numInputs, int numOutputs) {

        mTrainingDataList = trainingDataList;
        init();
        mNumInputs = numInputs;
        mNumOutputs = numOutputs;
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
        });
    }
}
