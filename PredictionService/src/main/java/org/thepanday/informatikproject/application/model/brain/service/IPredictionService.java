////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 20.11.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.model.brain.service;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;

import java.util.Arrays;

public interface IPredictionService {

    MultiLayerPerceptron prepareMultiLayerPerceptron(DataSet trainingDataSet);

    static void testPredictingMatches(MultiLayerPerceptron nnet, DataSet dset) {
        for (DataSetRow trainingElement : dset.getRows()) {
            nnet.setInput(trainingElement.getInput());
            nnet.calculate();
            double[] networkOutput = nnet.getOutput();

            System.out.print("Input: " + Arrays.toString(trainingElement.getInput()));
            System.out.println(" Output: " + Arrays.toString(networkOutput));
            System.out.println("Desired output: " + Arrays.toString(trainingElement.getDesiredOutput()));
        }
    }

    ;

    /**
     * Connect to web scraping service and save training data in a file.
     *
     * @return path of filename
     */
    public String getTrainingData();

    /**
     * Look online for new matches statistics.
     */
    public void updateTrainingData();

    // ------------------------------------------------------------------------
    // getters/setters
    // ------------------------------------------------------------------------

}
