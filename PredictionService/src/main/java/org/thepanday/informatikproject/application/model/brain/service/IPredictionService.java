////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 20.11.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.model.brain.service;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;

import java.util.Arrays;

/**
 *
 */
public interface IPredictionService {

    // ------------------------------------------------------------------------
    // constants
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------
    // TODO: structure what this service should do.
    //  create another class to handle training data. An UtilityClass?

    MultiLayerPerceptron prepareNeuralNetwork(DataSet trainingDataSet);

    static void testPredictingMatches(NeuralNetwork nnet, DataSet dset) {
        for (DataSetRow trainingElement : dset.getRows()) {
            nnet.setInput(trainingElement.getInput());
            nnet.calculate();
            double[] networkOutput = nnet.getOutput();
            System.out.print("Input: " + Arrays.toString(trainingElement.getInput()));
            System.out.println(" Output: " + Arrays.toString(networkOutput));
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

    public void getAverageMatchHistoryForTeam(String teamTitle);

    // ------------------------------------------------------------------------
    // getters/setters
    // ------------------------------------------------------------------------

}
