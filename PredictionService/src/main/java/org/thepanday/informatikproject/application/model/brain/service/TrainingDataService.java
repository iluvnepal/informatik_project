////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 26.01.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.model.brain.service;

import org.neuroph.util.data.norm.MaxMinNormalizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thepanday.informatikproject.common.data.TrainingDataSet;
import org.thepanday.informatikproject.common.entity.MatchStatEnum;
import org.thepanday.informatikproject.common.entity.jsonentities.MatchHistory;
import org.thepanday.informatikproject.common.entity.jsonentities.TeamDetail;
import org.thepanday.informatikproject.common.entity.jsonentities.TeamDetailEntries;
import org.thepanday.informatikproject.common.entity.jsonentities.TrainingData;
import org.thepanday.informatikproject.util.JsonDataUtility;
import org.thepanday.informatikproject.util.MatchHistoryUtility;
import org.thepanday.informatikproject.util.WebpageScrapingService;

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
import static org.thepanday.informatikproject.common.entity.MatchStatEnum.PPDA;
import static org.thepanday.informatikproject.common.entity.MatchStatEnum.PPDA_ALLOWED;

@Service
public class TrainingDataService implements ITrainingDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingDataService.class);

    public static final List<MatchStatEnum> INCLUDED_PARAMETERS = new LinkedList<>(Arrays.asList(HOME_AWAY,
                                                                                                 EXPECTED_GOALS,
                                                                                                 NON_PENALTY_EXPECTED_GOALS,
                                                                                                 PPDA,
                                                                                                 DEEP,
                                                                                                 EXPECTED_GOALS_AGAINST,
                                                                                                 NON_PENALTY_EXPECTED_GOALS_AGAINST,
                                                                                                 PPDA_ALLOWED,
                                                                                                 DEEP_ALLOWED,
                                                                                                 GOALS_SCORED,
                                                                                                 GOALS_CONCEIVED));
    /**
     * Output size is always 2 in our implementation as the result is always goals scored and goals conceived.
     */
    public static final int OUTPUT_SIZE = 2;
    public static final int INPUT_SIZE = INCLUDED_PARAMETERS.size() - OUTPUT_SIZE;

    @Autowired
    private WebpageScrapingService mWebpageScrapingService;

    private TeamDetailEntries mCurrentTeamDetailEntries;
    private final TeamDetailEntries mDefaultTeamDetailEntries;
    private final List<MatchHistory> mMatchHistories = new ArrayList<>();

    private Map<String, String> mTitleToId = new HashMap<>();

    public TrainingDataService() {
        mDefaultTeamDetailEntries = getDefaultTeamDetailEntries();
        mCurrentTeamDetailEntries = mDefaultTeamDetailEntries;
        this.init();
    }

    @Override
    public TrainingDataSet normalizeDataSet(TrainingDataSet dataSet) {
        MaxMinNormalizer normalizer = new MaxMinNormalizer(dataSet);
        normalizer.normalize(dataSet);

        return dataSet;
    }

    @Override
    public TeamDetailEntries getDefaultTeamDetailEntries() {
        final ClassLoader classLoader = this
            .getClass()
            .getClassLoader();
        final InputStream resourceAsStream = classLoader.getResourceAsStream(JsonDataUtility.ALL_DATA);
        final String jsonStringFromFile = JsonDataUtility.getStringFromFileInputStream(resourceAsStream);
        return JsonDataUtility.teamDetailEntriesJsonToObject(jsonStringFromFile);
    }

    @Override
    public void gatherAllTeamsDataOnline() {
        mCurrentTeamDetailEntries = mWebpageScrapingService.scrapeAllMatchesFromAllLeaguesAndYearsToTeamsDataContainer();
        LOGGER.info("Processing web-data complete. All match histories of {} teams were collected.",
                    mCurrentTeamDetailEntries
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
    public TrainingDataSet getTrainingDataSet() {
        return new TrainingDataSet(this.getTrainingData(), INPUT_SIZE, OUTPUT_SIZE);
    }

    @Override
    public TrainingDataSet getNormalizedDataSet() {
        TrainingDataSet dataSet = new TrainingDataSet(this.getTrainingData(), INPUT_SIZE, OUTPUT_SIZE);
        return this.normalizeDataSet(dataSet);
    }

    @Override
    public List<TrainingData> getInputForTeams(final String homeTeamName, final String awayTeamName) {
        final MatchHistory matchHistory = MatchHistoryUtility.mergeMatchHistoriesToInputData(mCurrentTeamDetailEntries
                                                                                                 .getTeamDetailForTeam(mTitleToId.get(homeTeamName))
                                                                                                 .getAverageMatchHistory(),
                                                                                             mCurrentTeamDetailEntries
                                                                                                 .getTeamDetailForTeam(mTitleToId.get(awayTeamName))
                                                                                                 .getAverageMatchHistory());
        return Collections.singletonList(MatchHistoryUtility.convertMatchHistoryToTrainingData(matchHistory));
    }

    private void init() {
        for (Map.Entry<String, TeamDetail> teamDetailEntry : mCurrentTeamDetailEntries
            .getTeamEntriesMap()
            .entrySet()) {
            final TeamDetail teamDetail = teamDetailEntry.getValue();
            // initialise title-to-id map
            mTitleToId.put(teamDetail.getId(), teamDetail.getTeamName());
            // collect all match histories
            mMatchHistories.addAll(teamDetail.getHistory());
        }

    }
}
