////////////////////////////////////////////////////////////////////////////////
//
// Created by kshitiz on 09.11.20.
//
// Copyright (c) 2006 - 2020 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.common.entity;

/**
 *
 */
public class Constants {

    private final String[] HISTORY_COLUMNS = { "h_a", "xG", "xGA", "npxG", "npxGA", "ppda_att", "ppda_def", "ppda_allowed_att", "ppda_allowed_def", "deep",
                                               "deep_allowed", "scored", "missed", "xpts", "result", "date", "wins", "draws", "loses", "pts", "npxGD" };

    public static final String HOME = "h_a";
    public static final String EXPECTED_GOALS = "xG";
    public static final String NON_PENALTY_EXPECTED_GOALS = "npxG";
    public static final String NON_PENALTY_EXPECTED_GOALS_AGAINST = "npxGA";
    public static final String PPDA_ATT = "ppda_att";
    public static final String PPDA_DEF = "ppda_def";
    public static final String PPDA_ALLOWED_ATT = "ppda_allowed_att";
    public static final String PPDA_ALLOWED_DEF = "ppda_allowed_def";
    public static final String DEEP = "deep";
    public static final String DEEP_ALLOWED = "deep_allowed";
    public static final String SCORED = "scored";
    public static final String CONCIEVED = "missed";
    public static final String EXPECTED_POINTS = "xpts";
    public static final String RESULT = "result";
    public static final String NON_PENALTY_EXPECTED_GOAL_DIFFERENCE = "npxGD";
}
