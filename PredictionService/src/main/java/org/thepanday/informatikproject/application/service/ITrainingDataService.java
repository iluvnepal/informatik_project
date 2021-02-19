////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 26.01.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thepanday.informatikproject.common.util.entity.TeamsContainer;
import org.thepanday.informatikproject.common.util.entity.TrainingData;

import java.util.List;

@Service
public interface ITrainingDataService {

    /**
     * Fetch teams data from www.understat.com and map data to {@link TeamsContainer}
     *
     * @return
     */
    public TeamsContainer getTeamsDataContainer();

    public List<TrainingData> getTrainingData();

    /**
     * Data extraction from understat.com
     * Note: will run in background.
     */
    @Async
    public void gatherAllTeamsDataAsynchronously();

    @Async
    public void gatherAllTrainingDataAsynchronously();

    public List<TrainingData> getTrainingDataForTeams(final String homeTeam, final String awayTeam);

}
