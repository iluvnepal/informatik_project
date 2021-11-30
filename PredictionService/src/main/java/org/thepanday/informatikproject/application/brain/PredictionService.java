////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 20.11.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.brain;

import org.springframework.beans.factory.annotation.Autowired;
import org.thepanday.informatikproject.common.util.UnderstatDataParser;
import org.thepanday.informatikproject.common.util.jsonentities.TeamDetail;
import org.thepanday.informatikproject.common.util.jsonentities.ScrapedPageContainer;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class PredictionService implements IPredictionService {

    // ------------------------------------------------------------------------
    // constants
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------
    @Autowired
    private UnderstatDataParser mUnderstatDataParser;

    @Override
    public String getTrainingData() {
        final ScrapedPageContainer allScrapedPageContainer = mUnderstatDataParser.scrapeAllMatchesFromAllLeaguesAndYearsToTeamsDataContainer();
        final Map<String, TeamDetail> teamEntries = allScrapedPageContainer.getTeamEntriesMap();
        final String[] leagueNames = mUnderstatDataParser.getLeagues();
        final Map<String, List<String>> leagueToleagueTeamsMap = mUnderstatDataParser.getLeagueTeamsMap();
        return null;
    }

    @Override
    public void updateTrainingData() {

    }

    // ------------------------------------------------------------------------
    // getters/setters
    // ------------------------------------------------------------------------

}
