////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 03.11.2020.
//
// Copyright (c) 2006 - 2020 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.util;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.thepanday.informatikproject.common.entity.jsonentities.TeamDetail;
import org.thepanday.informatikproject.common.entity.jsonentities.TeamDetailEntries;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WebpageScrapingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebpageScrapingService.class);

    private static final String BASE_URL = "https://understat.com/league";
    private static final String[] LEAGUES = { "La_liga", "EPL", "Bundesliga", "Serie_A", "Ligue_1", "RFPL" };
    private static final String[] YEARS = { "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021" };
    private static final String TEAMS_DATA_STRING = "var teamsData = JSON.parse('";

    private StringBuilder mJsonData = new StringBuilder();
    private Map<String, List<String>> mLeagueToTeams = new HashMap<>();

    public WebpageScrapingService() {
        this.populateLeagueTeamsMap();
    }

    public TeamDetailEntries scrapeAllMatchesFromAllLeaguesAndYearsToTeamsDataContainer() {
        final TeamDetailEntries teamDetailEntries = new TeamDetailEntries(new HashMap<>());
        LOGGER.info("Fetching data from:");
        for (String pageUrl : this.getPageUrls()) {
            LOGGER.info("Scraping page: {}", pageUrl);
            final TeamDetailEntries containerToAdd = this.pageToTeamsDataContainer(pageUrl);
            teamDetailEntries.addToEntries(containerToAdd);
        }
        LOGGER.info("Fetching data completed.");
        return teamDetailEntries;
    }

    private List<String> getPageUrls() {
        final ArrayList<String> pageUrls = new ArrayList<>();
        for (String league : LEAGUES) {
            for (String year : YEARS) {
                String url = BASE_URL + "/" + league + "/" + year;
                pageUrls.add(url);
            }
        }
        return pageUrls;
    }

    protected TeamDetailEntries pageToTeamsDataContainer(String url) {
        final HtmlPage page = this.getPage(url);

        final String pageAsString = this.decodeHex(page.asXml());
        final String dataAsJsonString = this.getTeamsDataAsJsonString(pageAsString);
        mJsonData.append(dataAsJsonString);
        return JsonDataUtility.teamDetailEntriesJsonToObject(dataAsJsonString);
    }

    private String getTeamsDataAsJsonString(final String pageAsString) {
        int start = pageAsString.indexOf(TEAMS_DATA_STRING);
        int end = pageAsString.indexOf("');");

        return pageAsString.substring(start + TEAMS_DATA_STRING.length(), end);
    }

    public void populateLeagueTeamsMap() {
        final String year = "2020";
        for (String league : LEAGUES) {
            String pageUrl = BASE_URL + "/" + league + "/" + year;
            final TeamDetailEntries yearTeamData = pageToTeamsDataContainer(pageUrl);
            List<String> leagueTeams = new ArrayList<>();
            for (Map.Entry<String, TeamDetail> idToEntry : yearTeamData
                .getTeamEntriesMap()
                .entrySet()) {
                leagueTeams.add(idToEntry
                                    .getValue()
                                    .getTeamName());
            }
            mLeagueToTeams.putIfAbsent(league, leagueTeams);
        }
    }

    protected HtmlPage getPage(String pageUrl) {
        try (WebClient client = new WebClient()) {
            client
                .getOptions()
                .setJavaScriptEnabled(false);
            client
                .getOptions()
                .setCssEnabled(false);
            client
                .getOptions()
                .setUseInsecureSSL(true);
            return client.getPage(pageUrl);
        } catch (IOException e) {
            LOGGER.error("Could not get page from URL: {}", pageUrl);
            return null;
        }
    }

    private String decodeHex(final String encodedString) {
        return StringEscapeUtils.unescapeJava(encodedString.replaceAll("\\\\x", "\\\\u00"));
    }

    /**
     * Returns a map whose keys represent name of the league and each value is the list of all teams in that league.
     *
     * @return
     */
    public Map<String, List<String>> getLeagueToTeams() {
        return mLeagueToTeams;
    }

    /**
     * list of all available leagues.
     *
     * @return
     */
    public String[] getLeagues() {
        return LEAGUES;
    }

    private void writeJsonToFile(String jsonString, String fileName) {
        try {
            final PrintWriter out = new PrintWriter(fileName);
            out.println(jsonString);
            out.close();
        } catch (FileNotFoundException e) {
            LOGGER.warn("Error while creating file: {}", fileName);
        }
    }

}
