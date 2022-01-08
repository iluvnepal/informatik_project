package org.thepanday.informatikproject.application.model.brain.service;

import org.junit.jupiter.api.*;
import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.thepanday.informatikproject.application.model.brain.config.TestConfig;
import org.thepanday.informatikproject.common.data.TrainingDataSet;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 20.11.2021.
//
////////////////////////////////////////////////////////////////////////////////

@ContextConfiguration(classes = { TestConfig.class })
@SpringBootTest
class PredictionServiceTest {

    @Autowired
    private IPredictionService mPredictionService;
    @Autowired
    private ITrainingDataService mTrainingDataService;

    @Test
    void testGetTrainingData() {
        assertNotNull(mPredictionService);
        final TrainingDataSet trainingDataSet = mTrainingDataService.getTrainingDataSet();
        //todo data set rows are empty. addInput needs to be called and dataset rows need to be created.
        final List<DataSet> split = Arrays.asList(trainingDataSet.split(0.1, 0.01, 0.89));
        System.out.println(MessageFormat.format("total data count: {0}", trainingDataSet.size()));
        split.forEach(m -> System.out.println(MessageFormat.format("split number {0} with {1} entries.", split.indexOf(m), m.size())));
        final MultiLayerPerceptron multiLayerPerceptron = mPredictionService.prepareNeuralNetwork(split.get(0));
        IPredictionService.testPredictingMatches(multiLayerPerceptron, split.get(1));

    }
}