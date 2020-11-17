package org.thepanday.informatikproject.application;

import org.thepanday.informatikproject.common.util.UnderstatDataParser;

public class Application {

    private final String[] HISTORY_COLUMNS = { "h_a", "xG", "xGA", "npxG", "npxGA", "ppda_att", "ppda_def", "ppda_allowed_att", "ppda_allowed_def", "deep",
                                               "deep_allowed", "scored", "missed", "xpts", "result", "date", "wins", "draws", "loses", "pts", "npxGD" };

    public static void main(String[] args) {
        final UnderstatDataParser understatDataParser = new UnderstatDataParser();
        understatDataParser.createTeamData();
    }
}
