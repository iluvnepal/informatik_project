////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 20.11.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.model.brain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.thepanday.informatikproject.common.entity.jsonentities.TeamDetail;
import org.thepanday.informatikproject.common.entity.jsonentities.TeamDetailEntries;
import org.thepanday.informatikproject.util.UnderstatDataParser;

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
        final TeamDetailEntries allTeamDetailEntries = mUnderstatDataParser.scrapeAllMatchesFromAllLeaguesAndYearsToTeamsDataContainer();
        final Map<String, TeamDetail> teamEntries = allTeamDetailEntries.getTeamEntriesMap();
        final String[] leagueNames = mUnderstatDataParser.getLeagues();
        final Map<String, List<String>> leagueToleagueTeamsMap = mUnderstatDataParser.getLeagueToTeams();
        return null;
    }

    @Override
    public void updateTrainingData() {

    }

    @Override
    public void getAverageMatchHistoryForTeam(String teamTitle) {
        // todo
    }

    // ------------------------------------------------------------------------
    // getters/setters
    // ------------------------------------------------------------------------

}
