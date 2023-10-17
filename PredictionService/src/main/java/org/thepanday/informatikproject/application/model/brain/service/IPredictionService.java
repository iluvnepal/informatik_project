////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 20.11.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.model.brain.service;

import deepnetts.net.FeedForwardNetwork;

import javax.visrec.ml.data.DataSet;

public interface IPredictionService {

    FeedForwardNetwork getNeuralNetwork(DataSet trainingDataSet);

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
