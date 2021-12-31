////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 26.01.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.model.brain.service;

import org.thepanday.informatikproject.common.entity.jsonentities.TeamDetailEntries;
import org.thepanday.informatikproject.common.entity.jsonentities.TrainingData;

import java.util.List;

public interface ITrainingDataService {

    /**
     * Fetch teams data from www.understat.com and map data to {@link TeamDetailEntries}
     *
     * @return
     */
    public TeamDetailEntries getDefaultTeamDetailEntries();

    public List<TrainingData> getTrainingData();

    /**
     * Data extraction from understat.com
     * Note: will run in background.
     */
    public void gatherAllTeamsDataOnline();

    public List<TrainingData> getTestDataForTeams(final String homeTeam, final String awayTeam);

}
