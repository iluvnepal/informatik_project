////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 03.11.2020.
//
// Copyright (c) 2006 - 2020 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.Page;
import org.thepanday.informatikproject.common.entity.MatchHistory;
import org.thepanday.informatikproject.common.entity.TeamData;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thepanday.informatikproject.common.util.entity.EntryContainer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class UnderstatDataParser {

    private static final String BASE_URL = "https://understat.com/league";
    private static final String[] LEAGUES = { "La_liga", "EPL", "Bundesliga", "Serie_A", "Ligue_1", "RFPL" };
    private static final String[] YEARS = { "2014", "2015", "2016", "2017", "2018", "2019" };
    private static final String TEAMS_DATA_STRING = "var teamsData = JSON.parse('";

    private static final Logger LOGGER = LoggerFactory.getLogger(UnderstatDataParser.class);

    public Map<Integer, TeamData> mTeamDataMap = new HashMap<Integer, TeamData>();

    public Map<Integer, TeamData> createTeamDataFromJsonFile(String filePath) {
        try {
            String fromFile = Files.readString(Paths.get(filePath), StandardCharsets.US_ASCII);
            JsonObject allTeamsMatchHistories = new JsonParser()
                .parse(fromFile)
                .getAsJsonObject();
            for (Map.Entry<String, JsonElement> stringJsonElementEntry : allTeamsMatchHistories.entrySet()) {
                final TeamData currentTeamData = new TeamData();
                final JsonObject teamDataJsonObject = (JsonObject) stringJsonElementEntry.getValue();
                String id = teamDataJsonObject
                    .get("id")
                    .toString();
                id = id.substring(1, id.length() - 1);
                currentTeamData.setId(id);
                currentTeamData.setTeamName(teamDataJsonObject
                                                .get("title")
                                                .toString());
                LOGGER.info("Creating team data for : {}", currentTeamData.getTeamName());
                JsonArray history = (JsonArray) teamDataJsonObject
                    .get("history")
                    .getAsJsonArray();
                for (JsonElement jsonObjectWithMatchHistory : history) {
                    final Set<Map.Entry<String, JsonElement>> singleMatchHistoryMap = jsonObjectWithMatchHistory
                        .getAsJsonObject()
                        .entrySet();
                    currentTeamData.addMatchHistory(new MatchHistory(singleMatchHistoryMap));
                }
                LOGGER.info("putting team data for : {}", currentTeamData.getTeamName());
                this.mTeamDataMap.put(Integer.parseInt(currentTeamData.getId()), currentTeamData);
            }
            LOGGER.info("collected match stats for {} teams", mTeamDataMap.size());
        } catch (JsonSyntaxException e) {
            LOGGER.warn("Error parsing json data file. Error cause: {}", e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mTeamDataMap.isEmpty()) {
            LOGGER.info("Team Data map seems to be empty");
        }
        return mTeamDataMap;
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

    public EntryContainer scrapeDataToEntryContainer() {
        for (String league : LEAGUES) {
            for (String year : YEARS) {
                String url = BASE_URL + "/" + league + "/" + year;
                String fileName = "resources/" + league + year;

                final HtmlPage page = this.getPage(url);

                final String pageAsString = this.decodeHex(page.asXml());
                final String dataAsJsonString = this.getTeamsDataAsJsonString(pageAsString);
                this.writeJsonToFile(dataAsJsonString, fileName);
                return this.toEntryContainer(dataAsJsonString);
            }
        }
        return null;
    }

    private String getTeamsDataAsJsonString(final String pageAsString) {
        int start = pageAsString.indexOf(TEAMS_DATA_STRING);
        int end = pageAsString.indexOf("');");

        return pageAsString.substring(start + TEAMS_DATA_STRING.length(), end);
    }

    private EntryContainer toEntryContainer(String jsonAsString) {
        var o = new ObjectMapper();
        try {
            return o.readValue(jsonAsString, EntryContainer.class);
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

}
