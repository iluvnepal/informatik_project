////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 30.12.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thepanday.informatikproject.application.model.brain.service.ITrainingDataService;
import org.thepanday.informatikproject.util.WebpageScrapingService;

import java.util.List;
import java.util.Map;

/**
 *
 */
@Component
public class ViewContentsGenerator {

    @Autowired
    private ITrainingDataService mTrainingDataService;
    @Autowired
    private WebpageScrapingService mWebpageScrapingService;

    public Map<String, List<String>> getTeamsList() {
        return mWebpageScrapingService.getLeagueToTeams();
    }
}
