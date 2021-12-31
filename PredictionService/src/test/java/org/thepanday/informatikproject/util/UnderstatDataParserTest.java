package org.thepanday.informatikproject.util;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.thepanday.informatikproject.application.model.brain.config.TestConfig;
import org.thepanday.informatikproject.common.entity.jsonentities.TeamDetailEntries;

import java.io.IOException;

////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 22.11.2021.
//
////////////////////////////////////////////////////////////////////////////////
@SpringBootTest
@ContextConfiguration(classes = { TestConfig.class })
class UnderstatDataParserTest {

    @Autowired
    private UnderstatDataParser mParser;

    @Test
    void scrapeAllMatchesFromAllLeaguesAndYearsToTeamsDataContainer() throws IOException {
        final String jsonStringFromResource = JsonDataUtility.getStringFromFileInputStream(this
                                                                                               .getClass()
                                                                                               .getClassLoader()
                                                                                               .getResourceAsStream(JsonDataUtility.ALL_DATA));
        final TeamDetailEntries teamEntries = JsonDataUtility.teamDetailEntriesJsonToObject(jsonStringFromResource);
        assert (teamEntries.getTeamEntriesMap() != null);
    }

    @Test
    void populateLeagueTeamsMap() {
        final TeamDetailEntries teamDetailEntries = mParser.scrapeAllMatchesFromAllLeaguesAndYearsToTeamsDataContainer();
        JsonDataUtility.teamDetailEntriesObjectToJsonFile("all_data_as_json.json", teamDetailEntries);
    }

    @Test
    void getLeagueTeamsMap() {
    }

    @Test
    void getLeagues() {
    }
}