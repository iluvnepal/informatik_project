////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 26.01.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.controller.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thepanday.informatikproject.util.WebpageScrapingService;

import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class FormService implements IFormService {

    @Autowired
    private WebpageScrapingService mDataParser;

    @Override
    public Map<String, List<String>> getTeamsForAllLeague() {
        if (mDataParser
            .getLeagueToTeams()
            .isEmpty()) {
            mDataParser.populateLeagueTeamsMap();
        }
        return mDataParser.getLeagueToTeams();
    }

    @Override
    public String[] getLeagues() {
        return mDataParser.getLeagues();
    }

}
