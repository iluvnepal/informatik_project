////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 04.11.2020.
//
// Copyright (c) 2006 - 2020 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package common.entity;

import com.google.gson.JsonElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Set;

/**
 *
 */
public class MatchHistory {
    private static final Logger LOGGER = LogManager.getLogger();

    private boolean mIsHomeGame;
    private int mGoalsScored;
    private int mGoalsConceived;

    public MatchHistory(Set<Map.Entry<String, JsonElement>> singleMatchHistoryMap) {
        init(singleMatchHistoryMap);
    }

    private void init(Set<Map.Entry<String, JsonElement>> singleMatchHistoryMap) {
        if (singleMatchHistoryMap.isEmpty()) {
            LOGGER.warn("History Map empty");
            return;
        }

        for (Map.Entry<String, JsonElement> statNameValueEntry : singleMatchHistoryMap) {
            final String statisticName = statNameValueEntry.getKey();
            final String statisticValue = statNameValueEntry
                .getValue()
                .toString();

            final MatchStat matchStatTypeForValue = MatchStat.getMatchStatForValue(statisticName);

            if (matchStatTypeForValue == null) {
                continue;
            }

            switch (matchStatTypeForValue) {
                case HOMEAWAY:
                    this.mIsHomeGame = "h".equalsIgnoreCase(statisticValue);
                    break;
                case GOALS_SCORED:
                    this.mGoalsScored = Integer.parseInt(statisticValue);
                    break;
                case GOALS_CONCEIVED:
                    this.mGoalsConceived = Integer.parseInt(statisticValue);
            }
        }

    }

    public enum MatchStat {
        HOMEAWAY("h_a"),
        GOALS_SCORED("scored"),
        GOALS_CONCEIVED("missed");

        public final String mMatchStatName;

        private MatchStat(String statisticName) {
            this.mMatchStatName = statisticName;
        }

        public static MatchStat getMatchStatForValue(String columnName) {
            for (MatchStat statName : MatchStat.values()) {
                if (columnName.equalsIgnoreCase(statName.getMatchStatName())) {
                    return statName;
                }
            }
            //            LOGGER.warning("The given column name: " + columnName + ", does not represent any match stat available.");
            return null;
        }

        public String getMatchStatName() {
            return mMatchStatName;
        }
    }

    public boolean isHomeGame() {
        return mIsHomeGame;
    }

    public void setHomeGame(boolean homeGame) {
        mIsHomeGame = homeGame;
    }

    public int getGoalsScored() {
        return mGoalsScored;
    }

    public void setGoalsScored(int goalsScored) {
        mGoalsScored = goalsScored;
    }

    public int getGoalsConceived() {
        return mGoalsConceived;
    }

    public void setGoalsConceived(int goalsConceived) {
        mGoalsConceived = goalsConceived;
    }

}
