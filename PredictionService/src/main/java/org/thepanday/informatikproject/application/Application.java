package org.thepanday.informatikproject.application;

import org.thepanday.informatikproject.common.entity.TeamData;
import org.thepanday.informatikproject.common.util.UnderstatDataParser;

import java.util.Map;

public class Application {

    public static void main(String[] args) {
        final UnderstatDataParser understatDataParser = new UnderstatDataParser();
        // todo: create proper logger slj4 or something.
        final Map<Integer, TeamData> teamDataFromJsonFile = understatDataParser.createTeamDataFromJsonFile("resources/scraped_match_data/Serie_A2019");
        //                understatDataParser.scrapeTeamsDataToJsonFile();
    }
}
