////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 26.01.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.common.entity.jsonentities;

import org.neuroph.core.data.DataSetRow;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class TrainingData {

    private List<Double> mInput = new ArrayList<>();
    private List<Double> mOutput = new ArrayList<>();
    private final int mInputSize;
    private final int mOutputSize;

    public TrainingData(int inputSize, int outputSize) {
        mInputSize = inputSize;
        mOutputSize = outputSize;
    }

    public void setDataRow(List<Double> dataRow) {
        if (dataRow.size() != mInputSize + mOutputSize) {
            throw new IllegalArgumentException(MessageFormat.format("Size of provided data row ({0}) is different than current training data row size ({1})",
                                                                    dataRow.size(),
                                                                    mInputSize + mOutputSize));
        }
        this.setInput(dataRow.subList(0, mInputSize));
        this.setOutput(dataRow.subList(mInputSize, dataRow.size()));
    }

    public List<Double> getInput() {
        return mInput;
    }

    public void setInput(List<Double> input) {
        if (input.size() != mInputSize) {
            throw new IllegalArgumentException(MessageFormat.format("Given input size ({0}) is different than current training data input size ({1}).",
                                                                    input.size(),
                                                                    mInputSize));
        }
        this.mInput = input;
    }

    public List<Double> getOutput() {
        return mOutput;
    }

    public void setOutput(List<Double> output) {
        if (output.size() != mOutputSize) {
            throw new IllegalArgumentException(MessageFormat.format("Given output size ({0}) is different than current training data output size ({1}).",
                                                                    output.size(),
                                                                    mOutputSize));
        }
        this.mOutput = output;
    }

    public static DataSetRow toDataSetRow(TrainingData trainingData) {
        final DataSetRow dataSetRow = new DataSetRow(trainingData
                                                         .getInput()
                                                         .stream()
                                                         .mapToDouble(Double::doubleValue)
                                                         .toArray());
        dataSetRow.setDesiredOutput(trainingData
                                        .getOutput()
                                        .stream()
                                        .mapToDouble(Double::doubleValue)
                                        .toArray());

        return dataSetRow;
    }
}
