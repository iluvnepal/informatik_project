////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 20.11.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.model.brain.service;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thepanday.informatikproject.application.model.brain.events.listeners.LearningRuleListener;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.Arrays;

@Service
public class PredictionService implements IPredictionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PredictionService.class);

    @Autowired
    private ITrainingDataService mTrainingDataService;

    public void createNeuralNetwork(TransferFunctionType transferFunctionType, int... neurons) {
        //todo
    }

    @Override
    public MultiLayerPerceptron prepareMultiLayerPerceptron(DataSet learningDataset) {
        // create multi layer perceptron
        // todo: create tests that change these parameters and test which is the best parameter
        MultiLayerPerceptron neuralNet = new MultiLayerPerceptron(TransferFunctionType.SIGMOID,
                                                                  TrainingDataService.INPUT_SIZE,
                                                                  3,
                                                                  TrainingDataService.OUTPUT_SIZE);
        // set learning parametars
        MomentumBackpropagation learningRule = (MomentumBackpropagation) neuralNet.getLearningRule();
        learningRule.setLearningRate(0.2);
        learningRule.setMomentum(0.2);
        learningRule.setMaxError(0.008);
        learningRule.setMaxIterations(300);
        learningRule.addListener(new LearningRuleListener());

        // learn the training set
        System.out.println("Training neural network...");
        neuralNet.learn(learningDataset);
        System.out.println("Done!");

        // test perceptron
        return neuralNet;
    }

    @Override
    public void testPredictingMatches(MultiLayerPerceptron nnet, DataSet dset) {
        for (DataSetRow trainingElement : dset.getRows()) {
            nnet.setInput(trainingElement.getInput());
            nnet.calculate();
            LOGGER.info(MessageFormat.format("Output: {0}, {1} desired output, network error: {2}",
                                             Arrays.toString(Arrays
                                                                 .stream(mTrainingDataService.denormalizeOutput(nnet.getOutput()))
                                                                 .mapToObj(d -> new DecimalFormat("0.00").format(d))
                                                                 .toArray()),
                                             Arrays.toString(mTrainingDataService.denormalizeOutput(trainingElement.getDesiredOutput())),
                                             nnet
                                                 .getLearningRule()
                                                 .getTotalNetworkError()));
        }
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
