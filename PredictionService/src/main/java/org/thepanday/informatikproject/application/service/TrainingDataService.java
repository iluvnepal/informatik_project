////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 26.01.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.thepanday.informatikproject.common.util.UnderstatDataParser;
import org.thepanday.informatikproject.common.util.entity.TeamDetail;
import org.thepanday.informatikproject.common.util.entity.TeamsContainer;
import org.thepanday.informatikproject.common.util.entity.History;
import org.thepanday.informatikproject.common.util.entity.TrainingData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class TrainingDataService implements ITrainingDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingDataService.class);

    @Autowired
    private UnderstatDataParser dataParser;

    private Map<String, TeamDetail> mCompleteTeamDataMap = new HashMap<>();
    public List<TrainingData> mCompleteTrainingDataList = new ArrayList<>();

    // todo: do i need this?
    @Override
    public TeamsContainer getTeamsDataContainer() {
        return dataParser.scrapeAllMatchesFromAllLeaguesAndYearsToTeamsDataContainer();
    }

    @Override
    public void gatherAllTeamsDataAsynchronously() {
        mCompleteTeamDataMap = dataParser
            .scrapeAllMatchesFromAllLeaguesAndYearsToTeamsDataContainer()
            .getTeamEntries();
        LOGGER.info("Training data sraping complete. TeamDataMap contains {} teams.", mCompleteTeamDataMap.size());
    }

    @Override
    public void gatherAllTrainingDataAsynchronously() {
        getTrainingData();
    }

    @Override
    public List<TrainingData> getTrainingData() {
        if (mCompleteTeamDataMap.isEmpty()) {
            LOGGER.info("Data map empty. Attempting to scrape data.");
            mCompleteTeamDataMap = dataParser
                .scrapeAllMatchesFromAllLeaguesAndYearsToTeamsDataContainer()
                .getTeamEntries();
            LOGGER.info("Training data sraping complete. TeamDataMap contains {} teams.", mCompleteTeamDataMap.size());
        }

        if (!mCompleteTrainingDataList.isEmpty()) {
            return mCompleteTrainingDataList;
        }
        // extract training data from all available match histories from each team.
        for (Map.Entry<String, TeamDetail> teamDetailMap : mCompleteTeamDataMap.entrySet()) {
            final List<TrainingData> trainingDataFromSingleTeamHistory = this.getTrainingDataFromSingleTeamHistory(teamDetailMap
                                                                                                                       .getValue()
                                                                                                                       .getHistory());
            mCompleteTrainingDataList.addAll(trainingDataFromSingleTeamHistory);
        }
        return mCompleteTrainingDataList;
    }

    private List<TrainingData> getTrainingDataFromSingleTeamHistory(List<History> historyList) {
        final List<TrainingData> trainingDataList = new ArrayList<>();
        for (History history : historyList) {
            trainingDataList.add(refineStatToTrainingData(history));
        }
        return trainingDataList;
    }

    @Override
    public List<TrainingData> getTrainingDataForTeams(final String homeTeam, final String awayTeam) {
        // todo get averate training stats from all previous matches of home and away teams.
        if (mCompleteTeamDataMap.isEmpty()) {
            this.gatherAllTeamsDataAsynchronously();
            return Collections.emptyList();
        }
        final TeamDetail homeTeamDetail = mCompleteTeamDataMap.get(homeTeam);
        final TeamDetail awayTeamDetail = mCompleteTeamDataMap.get(awayTeam);
        final List<TrainingData> homeTeamTrainingData = getTrainingDataFromSingleTeamHistory(homeTeamDetail.getHistory());
        final List<TrainingData> awayTeamTrainingData = getTrainingDataFromSingleTeamHistory(awayTeamDetail.getHistory());

        return List.of(getAverageFromTrainingData(homeTeamTrainingData), getAverageFromTrainingData(awayTeamTrainingData));
    }

    private TrainingData refineStatToTrainingData(History history) {
        final TrainingData t = new TrainingData();
        t
            .addInput("h".equals(history.getHA()) ? 1 : 0)
            .addInput(history.getXG())
            .addInput(history.getDeep())
            .addInput(history.getXGA())
            .addInput(history.getDeepAllowed());
        t.addOutput(history.getScored(), history.getMissed());
        return t;
    }

    private TrainingData getAverageFromTrainingData(List<TrainingData> dataList) {
        final TrainingData trainingData = new TrainingData();
        List<List<Double>> inputList = new ArrayList<>();
        dataList.forEach(t -> inputList.add(t.getInput()));
        List<Double> averageInput = new ArrayList<>(inputList
                                                        .get(0)
                                                        .size());
        for (int i = 0; i < inputList.size(); i++) {
            final List<Double> input = inputList.get(i);
            for (int j = 0; j < input.size(); i++) {
                double avg = input.get(j) + averageInput.get(j);
                averageInput.set(j, avg / 2);
            }
        }
        trainingData.setInput(averageInput);
        return trainingData;
    }
}
