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
        TrainingDataSet trainingDataSet = mTrainingDataService.getNormalizedDataSet();
        final List<DataSet> split = Arrays.asList(trainingDataSet.split(0.03, 0.97));
        split.forEach(m -> System.out.println(MessageFormat.format("split number {0} with {1} entries.", split.indexOf(m), m.size())));

        // todo : add some cases with another learning rate etc. create better method that accepts more parameters like learning rate, rule, etc.
        final MultiLayerPerceptron multiLayerPerceptron = mPredictionService.prepareMultiLayerPerceptron(split.get(1));
        mPredictionService.testPredictingMatches(multiLayerPerceptron, split.get(0));
    }
}