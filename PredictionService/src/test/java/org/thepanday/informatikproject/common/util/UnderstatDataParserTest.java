package org.thepanday.informatikproject.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.thepanday.informatikproject.common.util.jsonentities.TeamDetail;
import org.thepanday.informatikproject.common.util.jsonentities.ScrapedPageContainer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 22.11.2021.
//
////////////////////////////////////////////////////////////////////////////////

class UnderstatDataParserTest {

    @Test
    void scrapeAllMatchesFromAllLeaguesAndYearsToTeamsDataContainer() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ScrapedPageContainer scrapedPageContainer = mapper.readValue(Paths
                                                             .get(MatchDataUtility.DEFAULT_JSON_DATA_FILE)
                                                             .toFile(), ScrapedPageContainer.class);
        final ScrapedPageContainer tryData = mapper.readValue(Paths.get("try_data.json").toFile(), ScrapedPageContainer.class);
        final Map<String, TeamDetail> teamEntries = scrapedPageContainer.getTeamEntriesMap();
        assertThat(teamEntries).isNotEmpty();
    }

    @Test
    void populateLeagueTeamsMap() {
    }

    @Test
    void getLeagueTeamsMap() {
    }

    @Test
    void getLeagues() {
    }
}