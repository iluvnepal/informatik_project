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
    HOME_AWAY("h_a"),
    GOALS_SCORED("scored"),
    GOALS_CONCEIVED("missed"),
    EXPECTED_GOALS("xG"),
    NON_PENALTY_EXPECTED_GOALS("npxG"),
    NON_PENALTY_EXPECTED_GOALS_AGAINST("npxGA"),
    PPDA_ATT("ppda_att"),
    PPDA_DEF("ppda_def"),
    PPDA_ALLOWED_ATT("ppda_allowed_att"),
    PPDA_ALLOWED_DEF("ppda_allowed_def"),
    DEEP("deep"),
    DEEP_ALLOWED("deep_allowed"),
    EXPECTED_POINTS("xpts"),
    RESULT("result"),
    NON_PENALTY_EXPECTED_GOAL_DIFFERENCE("npxGD");

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
