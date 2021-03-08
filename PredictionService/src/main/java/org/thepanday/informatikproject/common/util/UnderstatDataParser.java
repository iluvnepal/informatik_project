////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 03.11.2020.
//
// Copyright (c) 2006 - 2020 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thepanday.informatikproject.common.entity.TeamData;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thepanday.informatikproject.common.util.entity.TeamDetail;
import org.thepanday.informatikproject.common.util.entity.TeamsContainer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class UnderstatDataParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnderstatDataParser.class);

    private static final String BASE_URL = "https://understat.com/league";
    private static final String[] LEAGUES = { "La_liga", "EPL", "Bundesliga", "Serie_A", "Ligue_1", "RFPL" };
    private static final String[] YEARS = { "2014", "2015", "2016", "2017", "2018", "2019", "2020" };
    private static final String TEAMS_DATA_STRING = "var teamsData = JSON.parse('";

    private Map<String, List<String>> leagueTeamsMap = new HashMap<>();

    public Map<Integer, TeamData> mTeamDataContainer = new HashMap<Integer, TeamData>();

    public UnderstatDataParser() {
        this.populateLeagueTeamsMap();
    }

    private HtmlPage getPage(String pageUrl) {
        WebClient client = new WebClient();
        client
            .getOptions()
            .setJavaScriptEnabled(false);
        client
            .getOptions()
            .setCssEnabled(false);
        client
            .getOptions()
            .setUseInsecureSSL(true);

        try {
            return client.getPage(pageUrl);
        } catch (IOException e) {
            LOGGER.error("Could not get page from URL: {}", pageUrl);
        }
        return null;
    }

    public TeamsContainer scrapeAllMatchesFromAllLeaguesAndYearsToTeamsDataContainer() {
        final TeamsContainer teamsContainer = new TeamsContainer(new HashMap<>());
        for (String league : LEAGUES) {
            for (String year : YEARS) {
                String url = BASE_URL + "/" + league + "/" + year;
                final TeamsContainer containerToAdd = this.pageToTeamsDataContainer(url);
                teamsContainer.addToEntries(containerToAdd);
            }
        }
        LOGGER.info("Scraping finished. Total Data Scraped : {}",
                    teamsContainer
                        .getTeamEntries()
                        .size());
        return teamsContainer;
    }

    private TeamsContainer pageToTeamsDataContainer(String url) {
        final HtmlPage page = this.getPage(url);

        final String pageAsString = this.decodeHex(page.asXml());
        final String dataAsJsonString = this.getTeamsDataAsJsonString(pageAsString);

        return this.jsonStringtoTeamsDataContainer(dataAsJsonString);
    }

    public void populateLeagueTeamsMap() {
        final String year = "2020";
        for (String league : LEAGUES) {
            String pageUrl = BASE_URL + "/" + league + "/" + year;
            final TeamsContainer yearTeamData = pageToTeamsDataContainer(pageUrl);
            List<String> leagueTeams = new ArrayList<>();
            for (Map.Entry<String, TeamDetail> idToEntry : yearTeamData
                .getTeamEntries()
                .entrySet()) {
                leagueTeams.add(idToEntry
                                    .getValue()
                                    .getTeamName());
            }
            leagueTeamsMap.putIfAbsent(league, leagueTeams);
        }
    }

    private String getTeamsDataAsJsonString(final String pageAsString) {
        int start = pageAsString.indexOf(TEAMS_DATA_STRING);
        int end = pageAsString.indexOf("');");

        return pageAsString.substring(start + TEAMS_DATA_STRING.length(), end);
    }

    private TeamsContainer jsonStringtoTeamsDataContainer(String jsonAsString) {
        var o = new ObjectMapper();
        try {
            return o.readValue(jsonAsString, TeamsContainer.class);
        } catch (JsonProcessingException e) {
            LOGGER.error("Could not parse json string.");
            e.printStackTrace();
        }

        return null;
    }

    private String decodeHex(final String encodedString) {
        return StringEscapeUtils.unescapeJava(encodedString.replaceAll("\\\\x", "\\\\u00"));
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

    public Map<String, List<String>> getLeagueTeamsMap() {
        return leagueTeamsMap;
    }

    public String[] getLeagues() {
        return LEAGUES;
    }

}
