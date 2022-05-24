////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 26.01.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.model.brain.service;

import org.thepanday.informatikproject.common.data.TrainingDataSet;
import org.thepanday.informatikproject.common.entity.jsonentities.TeamDetailEntries;
import org.thepanday.informatikproject.common.entity.jsonentities.TrainingData;

import java.util.List;

public interface ITrainingDataService {

    public TrainingDataSet normalizeDataSet(TrainingDataSet dataSet);

    /**
     * Fetch teams data from www.understat.com and map data to {@link TeamDetailEntries}
     *
     * @return
     */
    TeamDetailEntries getDefaultTeamDetailEntries();

    List<TrainingData> getTrainingData();

    TrainingDataSet getTrainingDataSet();

    /**
     * Data extraction from understat.com
     * Note: will run in background.
     */
    void gatherAllTeamsDataOnline();

    TrainingDataSet getNormalizedDataSet();

    /**
     * Get data for selected team that will be fed to network for prediction.
     *
     * @param homeTeam
     * @param awayTeam
     * @return
     */
    List<TrainingData> getInputForTeams(final String homeTeam, final String awayTeam);

}
