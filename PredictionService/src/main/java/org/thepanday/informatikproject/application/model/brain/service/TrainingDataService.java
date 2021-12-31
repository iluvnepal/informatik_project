////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 26.01.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.model.brain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thepanday.informatikproject.common.entity.jsonentities.TeamDetail;
import org.thepanday.informatikproject.common.entity.jsonentities.TeamDetailEntries;
import org.thepanday.informatikproject.common.entity.jsonentities.TrainingData;
import org.thepanday.informatikproject.util.JsonDataUtility;
import org.thepanday.informatikproject.util.UnderstatDataParser;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class TrainingDataService implements ITrainingDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingDataService.class);

    @Autowired
    private UnderstatDataParser mUnderstatDataParser;

    private TeamDetailEntries mCurrentTeamDetailEntries;
    private final TeamDetailEntries mDefaultTeamDetailEntries;

    private Map<String, String> mTitleToId = new HashMap<>();

    public TrainingDataService() {
        mDefaultTeamDetailEntries = getDefaultTeamDetailEntries();
        mCurrentTeamDetailEntries = mDefaultTeamDetailEntries;
        this.init();
    }

    @Override
    public TeamDetailEntries getDefaultTeamDetailEntries() {
        final ClassLoader classLoader = this
            .getClass()
            .getClassLoader();
        final InputStream resourceAsStream = classLoader.getResourceAsStream(JsonDataUtility.ALL_DATA);
        // todo! make this work. something with resource is not right.
        final String jsonStringFromFile = JsonDataUtility.getStringFromFileInputStream(resourceAsStream);
        return JsonDataUtility.teamDetailEntriesJsonToObject(jsonStringFromFile);
    }

    @Override
    public void gatherAllTeamsDataOnline() {
        mCurrentTeamDetailEntries = mUnderstatDataParser.scrapeAllMatchesFromAllLeaguesAndYearsToTeamsDataContainer();
        LOGGER.info("Processing web-data complete. All match histories of {} teams were collected.",
                    mCurrentTeamDetailEntries
                        .getTeamEntriesMap()
                        .size());
    }

    @Override
    public List<TrainingData> getTrainingData() {
        // todo: figure out first what the training data looks like relative to MatchHistory
        //  Implement Neuroph to a neural network and see what kind of data it takes as input and build training data model accordingly
        return null;
    }

    @Override
    public List<TrainingData> getTestDataForTeams(final String homeTeamName, final String awayTeamName) {
        //todo: figure out first what the training data looks like relative to MatchHistory
        mCurrentTeamDetailEntries
            .getTeamDetailForTeam(mTitleToId.get(homeTeamName))
            .getAverageMatchHistory();
        mCurrentTeamDetailEntries
            .getTeamDetailForTeam(mTitleToId.get(awayTeamName))
            .getAverageMatchHistory();
        return null;
    }

    private void init() {
        // initialise title-to-id map
        for (Map.Entry<String, TeamDetail> teamDetailEntry : mCurrentTeamDetailEntries
            .getTeamEntriesMap()
            .entrySet()) {
            final TeamDetail teamDetail = teamDetailEntry.getValue();
            mTitleToId.put(teamDetail.getId(), teamDetail.getTeamName());
        }
    }
}
