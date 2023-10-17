package org.thepanday.informatikproject.application.model.brain.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.thepanday.informatikproject.application.model.brain.config.TestConfig;
import org.thepanday.informatikproject.common.entity.jsonentities.TrainingData;

import java.util.List;
////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 25.09.2023.
//
////////////////////////////////////////////////////////////////////////////////

@SpringBootTest
@ContextConfiguration(classes = { TestConfig.class })
class TrainingDataServiceTest {

    @Autowired
    private ITrainingDataService mTrainingDataService;

    @Test
    void normalizeDataSet() {
    }

    @Test
    void denormalizeOutput() {
    }

    @Test
    void getDefaultTeamDetailEntries() {
    }

    @Test
    void gatherAllTeamsDataOnline() {
        mTrainingDataService.setIncludedParameters(TrainingDataService.INCLUDED_PARAMETERS_WIN_LOSS_DRAW);
        final List<TrainingData> trainingData = mTrainingDataService.getTrainingData();
        mTrainingDataService.writeMatchHistoriesToFile("src/test/resources/all-data-result.csv");
    }

    @Test
    void getTrainingData() {
    }

    @Test
    void getTrainingDataSet() {
    }

    @Test
    void getNormalizedDataSet() {
    }
}