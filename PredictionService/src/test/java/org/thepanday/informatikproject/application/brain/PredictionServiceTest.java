package org.thepanday.informatikproject.application.brain;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.thepanday.informatikproject.application.brain.config.TestConfig;
import org.thepanday.informatikproject.application.service.ITrainingDataService;
import org.thepanday.informatikproject.common.util.jsonentities.TrainingData;

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

        final List<TrainingData> trainingData = mTrainingDataService.getTrainingData();
        System.out.println("total data count: " + trainingData.size());

    }
}