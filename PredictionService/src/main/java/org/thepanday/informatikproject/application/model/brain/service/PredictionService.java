////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 20.11.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.model.brain.service;

import deepnetts.eval.Evaluators;
import deepnetts.net.FeedForwardNetwork;
import deepnetts.net.NeuralNetwork;
import deepnetts.net.layers.activation.ActivationType;
import deepnetts.net.loss.LossType;
import deepnetts.net.train.BackpropagationTrainer;
import deepnetts.util.FileIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.visrec.ml.data.DataSet;
import javax.visrec.ml.eval.EvaluationMetrics;
import java.io.File;
import java.io.IOException;

@Service
public class PredictionService implements IPredictionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PredictionService.class);

    @Autowired
    private ITrainingDataService mTrainingDataService;

    public void createNeuralNetwork() {
        //todo
    }

    @Override
    public FeedForwardNetwork getNeuralNetwork(DataSet dataSet) {
        // todo: create tests that change these parameters and test which is the best parameter
        // create instance of multi addLayer percetpron using builder
        FeedForwardNetwork neuralNet = FeedForwardNetwork
            .builder()
            .addInputLayer(TrainingDataService.INPUT_SIZE)
            .addFullyConnectedLayer(10, ActivationType.SIGMOID)     // sigmoid is best for prediction
            .addOutputLayer(2, ActivationType.SIGMOID)
            .lossFunction(LossType.MEAN_SQUARED_ERROR)
            .build();

        // create backpropagation trainer
        BackpropagationTrainer trainer = neuralNet.getTrainer();
        trainer.setMaxError(0.036f);
        trainer.setMaxEpochs(3000);
        trainer.setBatchMode(true);
        trainer.setLearningRate(0.2f);

        LOGGER.info("training neural net started..");
        neuralNet.train(dataSet);
        LOGGER.info("training neural net done.");

        final EvaluationMetrics em = Evaluators.evaluateClassifier(neuralNet, dataSet);
        System.out.println(em);
        return neuralNet;
    }

    public NeuralNetwork loadNeuralNetworkFromFile(String fileName) {
        try {
            return FileIO.createFromFile(new File(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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
