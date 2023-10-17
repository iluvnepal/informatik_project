////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 26.01.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.model.brain.service;

import deepnetts.data.DataSets;
import deepnetts.data.TabularDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.thepanday.informatikproject.common.data.TrainingDataSet;
import org.thepanday.informatikproject.common.entity.MatchStatEnum;
import org.thepanday.informatikproject.common.entity.jsonentities.MatchHistory;
import org.thepanday.informatikproject.common.entity.jsonentities.TeamMatchesContainer;
import org.thepanday.informatikproject.common.entity.jsonentities.TeamMatchesDetail;
import org.thepanday.informatikproject.common.entity.jsonentities.TrainingData;
import org.thepanday.informatikproject.util.JsonDataUtility;
import org.thepanday.informatikproject.util.MatchHistoryUtility;
import org.thepanday.informatikproject.util.WebpageScrapingService;

import javax.visrec.ml.data.DataSet;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.thepanday.informatikproject.common.entity.MatchStatEnum.DEEP;
import static org.thepanday.informatikproject.common.entity.MatchStatEnum.DEEP_ALLOWED;
import static org.thepanday.informatikproject.common.entity.MatchStatEnum.EXPECTED_GOALS;
import static org.thepanday.informatikproject.common.entity.MatchStatEnum.EXPECTED_GOALS_AGAINST;
import static org.thepanday.informatikproject.common.entity.MatchStatEnum.GOALS_CONCEIVED;
import static org.thepanday.informatikproject.common.entity.MatchStatEnum.GOALS_SCORED;
import static org.thepanday.informatikproject.common.entity.MatchStatEnum.HOME_AWAY;
import static org.thepanday.informatikproject.common.entity.MatchStatEnum.NON_PENALTY_EXPECTED_GOALS;
import static org.thepanday.informatikproject.common.entity.MatchStatEnum.NON_PENALTY_EXPECTED_GOALS_AGAINST;
import static org.thepanday.informatikproject.common.entity.MatchStatEnum.PPDA_ALLOWED_ATT;
import static org.thepanday.informatikproject.common.entity.MatchStatEnum.PPDA_ALLOWED_DEF;
import static org.thepanday.informatikproject.common.entity.MatchStatEnum.PPDA_ATT;
import static org.thepanday.informatikproject.common.entity.MatchStatEnum.PPDA_DEF;
import static org.thepanday.informatikproject.common.entity.MatchStatEnum.RESULT;

public class TrainingDataService implements ITrainingDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingDataService.class);

    public static final List<MatchStatEnum> INCLUDED_PARAMETERS_WIN_LOSS_DRAW = new LinkedList<>(Arrays.asList(HOME_AWAY,
                                                                                                               EXPECTED_GOALS,
                                                                                                               NON_PENALTY_EXPECTED_GOALS,
                                                                                                               EXPECTED_GOALS_AGAINST,
                                                                                                               NON_PENALTY_EXPECTED_GOALS_AGAINST,
                                                                                                               PPDA_ATT,
                                                                                                               PPDA_DEF,
                                                                                                               PPDA_ALLOWED_DEF,
                                                                                                               PPDA_ALLOWED_ATT,
                                                                                                               DEEP,
                                                                                                               DEEP_ALLOWED,
                                                                                                               RESULT));
    public static final List<MatchStatEnum> INCLUDED_PARAMETERS_SCORES_PREDICTION = new LinkedList<>(Arrays.asList(HOME_AWAY,
                                                                                                                   EXPECTED_GOALS,
                                                                                                                   NON_PENALTY_EXPECTED_GOALS,
                                                                                                                   EXPECTED_GOALS_AGAINST,
                                                                                                                   NON_PENALTY_EXPECTED_GOALS_AGAINST,
                                                                                                                   PPDA_ATT,
                                                                                                                   PPDA_DEF,
                                                                                                                   PPDA_ALLOWED_DEF,
                                                                                                                   PPDA_ALLOWED_ATT,
                                                                                                                   DEEP,
                                                                                                                   DEEP_ALLOWED,
                                                                                                                   GOALS_SCORED,
                                                                                                                   GOALS_CONCEIVED));
    public static int INPUT_SIZE = 11;
    public static int OUTPUT_SIZE;
    public static TabularDataSet CURRENT_RESULT_DATASET;
    public static TabularDataSet CURRENT_SCORE_DATASET;
    public static List<MatchStatEnum> IncludedParameters;

    @Autowired
    private WebpageScrapingService mWebpageScrapingService;

    private TeamMatchesContainer mCurrentTeamMatchesContainer;

    private TeamMatchesContainer mDefaultTeamMatchesContainer;
    private final List<MatchHistory> mMatchHistories = new ArrayList<>();
    private Map<String, String> mTitleToId = new HashMap<>();

    public TrainingDataService() {
        try {
            CURRENT_RESULT_DATASET = DataSets.readCsv("src/test/resources/all-data-result.csv", 11, 1, true, ",");
            CURRENT_SCORE_DATASET = DataSets.readCsv("src/test/resources/all-data.csv", 11, 2, true, ",");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        IncludedParameters = INCLUDED_PARAMETERS_SCORES_PREDICTION;
        OUTPUT_SIZE = IncludedParameters.size() - INPUT_SIZE;
        this.init();
    }

    @Override
    public DataSet normalizeDataSet(TrainingDataSet dataSet) {
        try {
            return dataSet.datasetFromCsv("csv/match-data.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public double[] denormalizeOutput(double[] outputVector) {
        // todo ? or delete
        return new double[] {};
    }

    @Override
    public TeamMatchesContainer getDefaultTeamDetailEntries() {
        final ClassLoader classLoader = this
            .getClass()
            .getClassLoader();
        final InputStream resourceAsStream = classLoader.getResourceAsStream(JsonDataUtility.ALL_DATA);
        final String jsonStringFromFile = JsonDataUtility.getStringFromFileInputStream(resourceAsStream);
        return JsonDataUtility.teamDetailEntriesJsonToObject(jsonStringFromFile);
    }

    @Override
    public void getDataFromOnlineSources() {
        mCurrentTeamMatchesContainer = mWebpageScrapingService.scrapeAllMatchesFromAllLeaguesAndYearsToTeamsDataContainer();
        LOGGER.info("Processing web-data complete. All match histories of {} teams were collected.",
                    mCurrentTeamMatchesContainer
                        .getTeamEntriesMap()
                        .size());
    }

    @Override
    public List<TrainingData> getTrainingData() {
        return mMatchHistories
            .stream()
            .map(MatchHistoryUtility::convertMatchHistoryToTrainingData)
            .collect(Collectors.toList());
    }

    @Override
    public List<TrainingData> getTrainingData(List<MatchStatEnum> includedParameters) {
        this.setIncludedParameters(includedParameters);
        return this.getTrainingData();
    }

    @Override
    public void writeMatchHistoriesToFile(String fileName) {
        try (BufferedWriter output = new BufferedWriter(new FileWriter(fileName, false))) {
            output.write(TrainingDataService.IncludedParameters
                             .stream()
                             .map(MatchStatEnum::getMatchStatName)
                             .collect(Collectors.joining(",")));
            output.append("\n");
            final String csvContent = mMatchHistories
                .stream()
                .map(MatchHistoryUtility::convertMatchHistoryToTrainingData)
                .map(TrainingData::getDataRow)
                .map(d -> d
                    .stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(",")))
                .collect(Collectors.joining("\n"));

            output.append(csvContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TrainingDataSet getTrainingDataSet() {
        return new TrainingDataSet(this.getTrainingData(), INPUT_SIZE, OUTPUT_SIZE);
    }

    @Override
    public DataSet getNormalizedDataSet() {
        TrainingDataSet dataSet = new TrainingDataSet(this.getTrainingData(), INPUT_SIZE, OUTPUT_SIZE);
        return this.normalizeDataSet(dataSet);
    }

    @Override
    public List<TrainingData> getInputForTeams(final String homeTeamName, final String awayTeamName) {
        final MatchHistory matchHistory = MatchHistoryUtility.mergeMatchHistoriesToInputData(mCurrentTeamMatchesContainer
                                                                                                 .getTeamDetailForTeam(mTitleToId.get(homeTeamName))
                                                                                                 .getAverageMatchHistory(),
                                                                                             mCurrentTeamMatchesContainer
                                                                                                 .getTeamDetailForTeam(mTitleToId.get(awayTeamName))
                                                                                                 .getAverageMatchHistory());
        return Collections.singletonList(MatchHistoryUtility.convertMatchHistoryToTrainingData(matchHistory));
    }

    private void init() {
        mDefaultTeamMatchesContainer = this.getDefaultTeamDetailEntries();
        mCurrentTeamMatchesContainer = mDefaultTeamMatchesContainer;
        for (Map.Entry<String, TeamMatchesDetail> teamDetailEntry : mCurrentTeamMatchesContainer
            .getTeamEntriesMap()
            .entrySet()) {
            final TeamMatchesDetail teamMatchesDetail = teamDetailEntry.getValue();
            // initialise title-to-id map
            mTitleToId.put(teamMatchesDetail.getId(), teamMatchesDetail.getTeamName());
            // collect all match histories
            mMatchHistories.addAll(teamMatchesDetail.getHistory());
        }

    }

    @Override
    public void setInputSize(int inputSize) {
        TrainingDataService.INPUT_SIZE = inputSize;
    }

    @Override
    public void setOutputSize(int outputSize) {
        TrainingDataService.OUTPUT_SIZE = outputSize;
    }

    @Override
    public List<MatchStatEnum> getIncludedParameters() {
        return IncludedParameters;
    }

    @Override
    public void setIncludedParameters(List<MatchStatEnum> includedParameters) {
        IncludedParameters = includedParameters;
        OUTPUT_SIZE = IncludedParameters.size() - INPUT_SIZE;
    }

}
