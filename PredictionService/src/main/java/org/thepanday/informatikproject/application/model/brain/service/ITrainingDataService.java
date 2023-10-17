////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 26.01.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.model.brain.service;

import org.springframework.stereotype.Service;
import org.thepanday.informatikproject.common.data.TrainingDataSet;
import org.thepanday.informatikproject.common.entity.MatchStatEnum;
import org.thepanday.informatikproject.common.entity.jsonentities.TeamMatchesContainer;
import org.thepanday.informatikproject.common.entity.jsonentities.TrainingData;

import javax.visrec.ml.data.DataSet;
import java.util.List;

@Service
public interface ITrainingDataService {

    public DataSet normalizeDataSet(TrainingDataSet dataSet);

    double[] denormalizeOutput(double[] outputVector);

    /**
     * Fetch teams data from www.understat.com and map data to {@link TeamMatchesContainer}
     *
     * @return
     */
    TeamMatchesContainer getDefaultTeamDetailEntries();
    List<TrainingData> getTrainingData(List<MatchStatEnum> includedParameters);
    List<TrainingData> getTrainingData();

    void writeMatchHistoriesToFile(String fileName);

    TrainingDataSet getTrainingDataSet();

    /**
     * Data extraction from understat.com
     * Note: will run in background.
     */
    void getDataFromOnlineSources();

    DataSet getNormalizedDataSet();

    /**
     * Get data for selected team that will be fed to network for prediction.
     *
     * @param homeTeam
     * @param awayTeam
     * @return
     */
    List<TrainingData> getInputForTeams(final String homeTeam, final String awayTeam);

    void setInputSize(int inputSize);

    void setOutputSize(int outputSize);

    List<MatchStatEnum> getIncludedParameters();

    void setIncludedParameters(List<MatchStatEnum> includedParameters);
}
