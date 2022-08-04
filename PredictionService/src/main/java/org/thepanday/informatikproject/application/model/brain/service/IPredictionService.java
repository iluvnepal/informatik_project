////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 20.11.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.model.brain.service;

import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.MultiLayerPerceptron;

public interface IPredictionService {

    MultiLayerPerceptron prepareMultiLayerPerceptron(DataSet trainingDataSet);

    void testPredictingMatches(MultiLayerPerceptron nnet, DataSet dset);

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
