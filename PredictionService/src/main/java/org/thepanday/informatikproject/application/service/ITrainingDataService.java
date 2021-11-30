////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 26.01.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.service;

import org.springframework.scheduling.annotation.Async;
import org.thepanday.informatikproject.common.util.jsonentities.ScrapedPageContainer;
import org.thepanday.informatikproject.common.util.jsonentities.TrainingData;

import java.util.List;

public interface ITrainingDataService {

    /**
     * Fetch teams data from www.understat.com and map data to {@link ScrapedPageContainer}
     *
     * @return
     */
    public ScrapedPageContainer getTeamsDataContainer();

    public List<TrainingData> getTrainingData();

    /**
     * Data extraction from understat.com
     * Note: will run in background.
     */
    @Async
    public void gatherAllTeamsDataAsynchronously();

    @Async
    public void gatherAllTrainingDataAsynchronously();

    public boolean isInitialised();

    public List<TrainingData> getTrainingDataForTeams(final String homeTeam, final String awayTeam);

}
