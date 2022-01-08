////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 30.12.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.common.entity;

/**
 *
 */
public enum MatchStatEnum {
    // match output
    GOALS_SCORED("scored"),
    GOALS_CONCEIVED("missed"),
    // home team stats
    HOME_AWAY("h_a"),
    EXPECTED_GOALS("xG"),
    NON_PENALTY_EXPECTED_GOALS("npxG"),
    /** Passes Per Defensive action - <b>home team</b> */
    PPDA("ppda"),
    /** Defensive action count - <b>home team</b> */
    PPDA_DEF("ppda_def"),
    /** Passes count - <b>home team</b> */
    PPDA_ATT("ppda_att"),
    DEEP("deep"),
    // away team stats,
    EXPECTED_GOALS_AGAINST("xGA"),
    NON_PENALTY_EXPECTED_GOALS_AGAINST("npxGA"),
    /** Passes Per Defensive action - <b>away team</b> */
    PPDA_ALLOWED("ppda_allowed"),
    /** Defensive action count - <b>away team</b> */
    PPDA_ALLOWED_DEF("ppda_allowed_def"),
    /** Passes count - <b>away team</b> */
    PPDA_ALLOWED_ATT("ppda_allowed_att"),
    DEEP_ALLOWED("deep_allowed"),
    // post match results
    EXPECTED_POINTS("xpts"),
    RESULT("result"),
    DATE("date");

    public final String mMatchStatName;

    MatchStatEnum(String statisticName) {
        this.mMatchStatName = statisticName;
    }

    public static MatchStatEnum convertStringToEnum(String columnName) {
        for (MatchStatEnum statName : MatchStatEnum.values()) {
            if (columnName.equalsIgnoreCase(statName.getMatchStatName())) {
                return statName;
            }
        }
        return null;
    }

    public String getMatchStatName() {
        return mMatchStatName;
    }
}
