package org.thepanday.informatikproject.application.model.brain.service;

import deepnetts.data.DataSets;
import deepnetts.data.TabularDataSet;
import deepnetts.data.preprocessing.scale.MaxScaler;
import deepnetts.eval.Evaluators;
import deepnetts.eval.RegresionEvaluator;
import deepnetts.net.FeedForwardNetwork;
import deepnetts.net.NeuralNetwork;
import deepnetts.net.layers.activation.ActivationType;
import deepnetts.net.loss.LossType;
import deepnetts.net.train.BackpropagationTrainer;
import deepnetts.util.FileIO;
import deepnetts.util.Tensor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.thepanday.informatikproject.application.model.brain.config.TestConfig;
import org.thepanday.informatikproject.common.data.TrainingDataSet;
import org.thepanday.informatikproject.common.entity.jsonentities.TrainingData;

import javax.visrec.ml.data.DataSet;
import javax.visrec.ml.eval.EvaluationMetrics;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 20.11.2021.
//
////////////////////////////////////////////////////////////////////////////////

@SpringBootTest
@ContextConfiguration(classes = { TestConfig.class })
class PredictionServiceTest {

    @Autowired
    private IPredictionService mPredictionService;
    @Autowired
    private ITrainingDataService mTrainingDataService;


    @Test
    void testGetTrainingData() throws IOException {
        final TabularDataSet dataSet = DataSets.readCsv("src/test/resources/match-data.csv", 9, 2, false, ",");
        dataSet.shuffle();
        // todo : add some cases with another learning rate etc. create better method that accepts more parameters like learning rate, rule, etc.

        FeedForwardNetwork neuralNet = FeedForwardNetwork
            .builder()
            .addInputLayer(9)
            .addFullyConnectedLayer(10, ActivationType.SIGMOID)     // sigmoid is best for prediction
            .addOutputLayer(2, ActivationType.SIGMOID)
            .lossFunction(LossType.MEAN_SQUARED_ERROR)
            .build();

        // create backpropagation trainer
        BackpropagationTrainer trainer = neuralNet.getTrainer();
        trainer.setMaxError(0.036f);
        trainer.setMaxEpochs(3000);
        trainer.setBatchMode(false);
        trainer.setLearningRate(0.2f);

        neuralNet.train(dataSet);

        final EvaluationMetrics em = Evaluators.evaluateRegressor(neuralNet, dataSet);
        System.out.println(em);

        FileIO.writeToFile(neuralNet, "src/test/resources/neuralnetworks/1.net");
    }

    @Test
    void testManipulateDataset() throws IOException {
        mTrainingDataService.setIncludedParameters(TrainingDataService.INCLUDED_PARAMETERS_WIN_LOSS_DRAW);
        final TabularDataSet dataSet = TrainingDataService.CURRENT_RESULT_DATASET;
        final MaxScaler maxScaler = new MaxScaler(dataSet);
        maxScaler.apply(dataSet);
        dataSet.shuffle();
        final List items = dataSet.getItems();

        final DataSet[] trainTestSplit = DataSets.trainTestSplit(dataSet, 0.8f);
        DataSet trainSet = trainTestSplit[0];
        DataSet testSet = trainTestSplit[1];

        FeedForwardNetwork neuralNet = FeedForwardNetwork
            .builder()
            .addInputLayer(TrainingDataService.INPUT_SIZE)
            .addFullyConnectedLayer(8, ActivationType.SIGMOID)     // sigmoid is best for prediction
            .addOutputLayer(TrainingDataService.OUTPUT_SIZE, ActivationType.SIGMOID)
            .lossFunction(LossType.MEAN_SQUARED_ERROR)
            .build();

        // create backpropagation trainer
        BackpropagationTrainer trainer = neuralNet.getTrainer();
        trainer.setMaxError(0.0041f);
        trainer.setMaxEpochs(3000);
        trainer.setBatchMode(false);
        trainer.setLearningRate(0.05f);

        neuralNet.train(trainSet);

        final EvaluationMetrics em = neuralNet.test(testSet);
        System.out.println(em);
        testSet.shuffle();
        int count = 0;
        // todo: check what should be good values for
        //  RSquared: 0.40721393
        //  MeanSquaredError: 0.11094838
        //  ResidualStandardError: 0.33314323
        //  FStatistics: 383.8797
        for (Object item : testSet.getItems()) {
            final Tensor input = ((TabularDataSet.Item) item).getInput();
            final Tensor output = ((TabularDataSet.Item) item).getTargetOutput();
            System.out.println("Input: " + Arrays.toString(input.getValues()));
            System.out.println("Targeted Output: " + Arrays.toString(output.getValues()));
            final float[] testOutput = neuralNet.predict(input.getValues());
            System.out.println("Normalised output: " + Arrays.toString(testOutput));
            final Tensor outputs = new Tensor(testOutput);
            maxScaler.deNormalizeOutputs(outputs);
            System.out.println("Denormalised output: " + outputs);
            count++;
            if(count ==10)
                break;
        }

//        final Tensor prediction = new Tensor(predict);
//        System.out.println("Normalised output: " + prediction);
//        maxScaler.deNormalizeOutputs(prediction);
//        System.out.println("Actual stat: 1.0,1.41484,1.41484,0.760531,0.760531,105.0,28.0,19.0,362.0,19.0,4.0,3.0,0.0\n");
//        System.out.println("prediction: " + prediction);
//        final RegresionEvaluator evaluator = new RegresionEvaluator();
//        final EvaluationMetrics emetrics = evaluator.evaluate(neuralNet, testSet);
//        System.out.println("Regression Evaluation Metrics");
//        System.out.println(emetrics);
        FileIO.writeToFileAsJson(neuralNet, "src/test/resources/neuralnetworks/trainedNetwork.json");
    }

    @Test
    void testLoadNeuralNetwork() throws IOException, ClassNotFoundException {
        final TabularDataSet dataSet = DataSets.readCsv("src/test/resources/all-data.csv", 11, 2, true, ",");
        final NeuralNetwork neuralNetwork = FileIO.createFromFile(new File("src/test/resources/neuralnetworks/1.net"));

        dataSet.shuffle();
        final DataSet[] split = dataSet.split(0.8);
        final EvaluationMetrics test = neuralNetwork.test(split[1]);
        System.out.println(test);

    }
}