////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 06.01.2022.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.common.data;

import org.neuroph.core.data.DataSet;
import org.thepanday.informatikproject.common.entity.jsonentities.TrainingData;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TrainingDataSet extends DataSet {

    private List<TrainingData> mTrainingDataList = new ArrayList<>();

    public TrainingDataSet(List<TrainingData> trainingDataList, int inputSize, int outputSize) {
        super(inputSize, outputSize);
        mTrainingDataList = trainingDataList;
    }

    public TrainingDataSet(int inputSize) {
        super(inputSize);
    }

    public TrainingDataSet(int inputSize, int outputSize) {
        super(inputSize, outputSize);
    }

}
