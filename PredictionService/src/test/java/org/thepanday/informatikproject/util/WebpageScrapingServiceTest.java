package org.thepanday.informatikproject.util;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.thepanday.informatikproject.application.model.brain.config.TestConfig;
import org.thepanday.informatikproject.common.entity.jsonentities.TeamMatchesContainer;

import java.io.IOException;

////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 22.11.2021.
//
////////////////////////////////////////////////////////////////////////////////
@SpringBootTest
@ContextConfiguration(classes = { TestConfig.class })
class WebpageScrapingServiceTest {

    @Autowired
    private WebpageScrapingService mParser;

    @Test
    void scrapeAllMatchesFromAllLeaguesAndYearsToTeamsDataContainer() throws IOException {
        final String jsonStringFromResource = JsonDataUtility.getStringFromFileInputStream(this
                                                                                               .getClass()
                                                                                               .getClassLoader()
                                                                                               .getResourceAsStream(JsonDataUtility.ALL_DATA));
        final TeamMatchesContainer teamEntries = JsonDataUtility.teamDetailEntriesJsonToObject(jsonStringFromResource);
        assert (teamEntries.getTeamEntriesMap() != null);
    }

    @Test
    void populateLeagueTeamsMap() {
        final TeamMatchesContainer teamMatchesContainer = mParser.scrapeAllMatchesFromAllLeaguesAndYearsToTeamsDataContainer();
        JsonDataUtility.teamDetailEntriesObjectToJsonFile("all_data_as_json.json", teamMatchesContainer);
    }

    @Test
    void getLeagueTeamsMap() {
    }

    @Test
    void getLeagues() {
    }
}