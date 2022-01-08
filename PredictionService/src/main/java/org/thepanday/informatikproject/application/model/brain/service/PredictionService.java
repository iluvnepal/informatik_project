////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 20.11.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.model.brain.service;

import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 */
public class PredictionService implements IPredictionService {

    @Autowired
    private ITrainingDataService mTrainingDataService;

    @Override
    public MultiLayerPerceptron prepareNeuralNetwork(DataSet trainingDataSet) {
        // todo:
        // create multi layer perceptron
        System.out.println("Creating neural network");
        MultiLayerPerceptron neuralNet = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, 85, 18, 9);

        // set learning parametars
        MomentumBackpropagation learningRule = (MomentumBackpropagation) neuralNet.getLearningRule();
        learningRule.setLearningRate(0.1);
        learningRule.setMomentum(0.2);

        // learn the training set
        System.out.println("Training neural network...");
        neuralNet.learn(trainingDataSet);
        System.out.println("Done!");

        // test perceptron
        return neuralNet;
    }

    @Override
    public String getTrainingData() {
        // todo : who knows!
        return null;
    }

    @Override
    public void updateTrainingData() {
        // not yet implemented
    }

    @Override
    public void getAverageMatchHistoryForTeam(String teamTitle) {
        // todo : maybe remove as other services exist for this purpose
    }

    // ------------------------------------------------------------------------
    // getters/setters
    // ------------------------------------------------------------------------

}
