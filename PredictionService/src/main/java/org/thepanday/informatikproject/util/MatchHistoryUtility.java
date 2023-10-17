////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 30.12.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.util;

import org.thepanday.informatikproject.application.model.brain.service.TrainingDataService;
import org.thepanday.informatikproject.common.entity.MatchStatEnum;
import org.thepanday.informatikproject.common.entity.jsonentities.MatchHistory;
import org.thepanday.informatikproject.common.entity.jsonentities.TrainingData;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MatchHistoryUtility {

    /**
     * Cherry-picks home and away team match statistics and merges them into one history
     *
     * @param homeTeamAverage
     * @param awayTeamAverage
     * @return
     */
    public static MatchHistory mergeMatchHistoriesToInputData(MatchHistory homeTeamAverage, MatchHistory awayTeamAverage) {
        final MatchHistory matchHistory = new MatchHistory();
        matchHistory.setHA("h");
        matchHistory.setXG(homeTeamAverage.getXG());
        matchHistory.setNpxG(homeTeamAverage.getNpxG());
        matchHistory.setPpda(homeTeamAverage.getPpda());
        matchHistory.setDeep(homeTeamAverage.getDeep());
        // set away team stats
        matchHistory.setXGA(awayTeamAverage.getXG());
        matchHistory.setNpxGA(awayTeamAverage.getNpxG());
        matchHistory.setPpdaAllowed(awayTeamAverage.getPpda());
        matchHistory.setDeepAllowed(awayTeamAverage.getDeep());
        return matchHistory;
    }

    public static TrainingData convertMatchHistoryToTrainingData(MatchHistory history) {
        final TrainingData trainingData = new TrainingData(TrainingDataService.INPUT_SIZE, TrainingDataService.OUTPUT_SIZE);
        final ArrayList<Double> dataRow = MatchHistoryUtility.getDoublesFromHistory(history);
        trainingData.setDataRow(dataRow);
        return trainingData;
    }

    private static ArrayList<Double> getDoublesFromHistory(MatchHistory history) {
        final ArrayList<Double> dataRow = new ArrayList<>();
        for (MatchStatEnum includedParameter : TrainingDataService.IncludedParameters) {
            dataRow.add(MatchHistoryUtility.resolveStringsToDouble(includedParameter, history.get(includedParameter)));
        }
        return dataRow;
    }

    public static MatchHistory getAverageMatchHistory(MatchHistory historyA, MatchHistory historyB) {
        // todo: use neuroph's normalizer tools to get mean. When the conversion of match history to training data is done, can be done.
        return null;
    }

    public static MatchHistory getAverageMatchHistory(List<MatchHistory> matchHistories) {
        MatchHistory averageMatchHistory = null;
        for (MatchHistory history : matchHistories) {
            if (averageMatchHistory == null) {
                averageMatchHistory = history;
                continue;
            }
            averageMatchHistory = getAverageMatchHistory(averageMatchHistory, history);
        }
        return averageMatchHistory;
    }

    private static double resolveStringsToDouble(MatchStatEnum statEnum, Object value) {
        if (statEnum == MatchStatEnum.HOME_AWAY) {
            return "h".equals(value.toString()) ? 1 : 0;
        }
        if (statEnum == MatchStatEnum.RESULT) {
            switch (value.toString()) {
                case "l":
                    return 0;
                case "d":
                    return 0.5;
                case "w":
                    return 1;
            }
        }
        return value == null ? 0 : Double.parseDouble(value.toString());
    }

}
