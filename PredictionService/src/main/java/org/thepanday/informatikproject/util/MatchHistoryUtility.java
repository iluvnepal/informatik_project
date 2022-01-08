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

import java.util.List;

/**
 *
 */
public class MatchHistoryUtility {

    /**
     * Cherry picks home and away team match statistics and merges them into one history
     *
     * @param homeTeamAverage
     * @param awayTeamAverage
     * @return
     */
    public static MatchHistory mergeMatchHistoriesToInputData(MatchHistory homeTeamAverage, MatchHistory awayTeamAverage) {
        final MatchHistory m = new MatchHistory();
        m.setHA("h");
        m.setXG(homeTeamAverage.getXG());
        m.setNpxG(homeTeamAverage.getNpxG());
        m.setPpda(homeTeamAverage.getPpda());
        m.setDeep(homeTeamAverage.getDeep());
        // set away team stats
        m.setXGA(awayTeamAverage.getXG());
        m.setNpxGA(awayTeamAverage.getNpxG());
        m.setPpdaAllowed(awayTeamAverage.getPpda());
        m.setDeepAllowed(awayTeamAverage.getDeep());
        return m;
    }

    public static TrainingData convertMatchHistoryToTrainingData(MatchHistory history) {
        final TrainingData trainingData = new TrainingData();
        for (MatchStatEnum includedParameter : TrainingDataService.INCLUDED_PARAMETERS) {
            trainingData.addInput(resolveStringsToDouble(includedParameter, history.get(includedParameter)));
        }
        return trainingData;
    }

    public static MatchHistory getAverageMatchHistory(MatchHistory historyA, MatchHistory historyB) {
        // todo: use neuroph's normalizer tools to get mean. When the convertion of match history to training data is done, can be done.
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
        return value == null ? 0 : Double.parseDouble(value.toString());
    }

    public static MatchHistory normaliseMatchHistory(List<MatchHistory> matchHistories) {
        // todo
        return null;
    }

}
