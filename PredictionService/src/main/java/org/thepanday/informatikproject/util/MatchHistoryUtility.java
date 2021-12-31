////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 30.12.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.util;

import org.thepanday.informatikproject.common.entity.jsonentities.MatchHistory;

import java.util.List;

/**
 *
 */
public class MatchHistoryUtility {

    public static MatchHistory getAverageMatchHistory(MatchHistory historyA, MatchHistory historyB) {
        // todo
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

    public static MatchHistory normaliseMatchHistory(List<MatchHistory> matchHistories) {
        // todo
        return null;
    }

}
