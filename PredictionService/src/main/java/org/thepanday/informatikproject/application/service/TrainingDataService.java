////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 26.01.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import java.util.function.Predicate;

/**
 *
 */
@Service
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
    public boolean isInitialised() {
        return !mCompleteTeamDataMap.isEmpty();
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
        // extract training data from all available match histori\es from each team.
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
    public List<TrainingData> getTrainingDataForTeams(final String homeTeamName, final String awayTeamName) {
        // todo get averate training stats from all previous matches of home and away teams.
        if (mCompleteTeamDataMap.isEmpty()) {
            this.gatherAllTeamsDataAsynchronously();
            return Collections.emptyList();
        }

        final TeamDetail homeTeamDetail = mCompleteTeamDataMap.get(getTeamIdForTeamName(homeTeamName));
        final TeamDetail awayTeamDetail = mCompleteTeamDataMap.get(getTeamIdForTeamName(awayTeamName));
        final List<TrainingData> homeTeamTrainingData = getTrainingDataFromSingleTeamHistory(homeTeamDetail.getHistory());
        final List<TrainingData> awayTeamTrainingData = getTrainingDataFromSingleTeamHistory(awayTeamDetail.getHistory());

        return List.of(getAverageFromTrainingData(homeTeamTrainingData), getAverageFromTrainingData(awayTeamTrainingData));
    }

    /**
     * Returns team id (key in {@link #mCompleteTeamDataMap}) for a given team name.
     *
     * @param teamName
     * @return teamId or {@code null} if no team id found for team name.
     */
    private String getTeamIdForTeamName(String teamName) {
        for (Map.Entry<String, TeamDetail> teamIdTeamDetailEntry : mCompleteTeamDataMap.entrySet()) {
            if (teamIdTeamDetailEntry
                .getValue()
                .getTeamName()
                .equals(teamName)) {
                return teamIdTeamDetailEntry.getKey();
            }
        }
        return null;
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
        int colIndex = 0;
        dataList.forEach(t -> inputList.add(t.getInput()));
        int inputCount = inputList
            .get(0)
            .size();
        List<Double> averageInput = new ArrayList<>(inputCount);
        while (colIndex < inputCount) {
            List<Double> singleInputToAvg = new ArrayList<>();
            for (int i = 0; i < inputList.size(); i++) {
                singleInputToAvg.add(inputList
                                         .get(i)
                                         .get(colIndex));
            }
            final Double avg = singleInputToAvg
                .stream()
                .reduce((a, b) -> (a + b) / 2)
                .orElse(0.0);
            averageInput.add(avg);
            colIndex++;
        }

        trainingData.setInput(averageInput);
        return trainingData;
    }
}
