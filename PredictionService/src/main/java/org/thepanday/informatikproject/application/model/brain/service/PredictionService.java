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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thepanday.informatikproject.application.config.listeners.PredictionEventListener;

@Service
public class PredictionService implements IPredictionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PredictionService.class);

    @Autowired
    private ITrainingDataService mTrainingDataService;

    @Override
    public MultiLayerPerceptron prepareMultiLayerPerceptron(DataSet learningDataset) {
        // create multi layer perceptron
        MultiLayerPerceptron neuralNet = new MultiLayerPerceptron(TransferFunctionType.SIGMOID,
                                                                  TrainingDataService.INPUT_SIZE,
                                                                  2,
                                                                  TrainingDataService.OUTPUT_SIZE);
        // set learning parametars
        MomentumBackpropagation learningRule = (MomentumBackpropagation) neuralNet.getLearningRule();
        learningRule.setLearningRate(0.1);
        learningRule.setMomentum(0.2);
        learningRule.setMaxError(0.009);
        learningRule.setMinErrorChangeIterationsLimit(3000);
        learningRule.addListener(new PredictionEventListener());

        // learn the training set
        System.out.println("Training neural network...");
        neuralNet.learn(learningDataset);
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

    // ------------------------------------------------------------------------
    // getters/setters
    // ------------------------------------------------------------------------

}
