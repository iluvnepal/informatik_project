////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 03.11.2020.
//
// Copyright (c) 2006 - 2020 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package common.util;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import common.entity.MatchHistory;
import common.entity.TeamData;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
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

    private static final Logger LOGGER = LogManager.getLogger(UnderstatDataParser.class);

    private static final String BASE_URL = "https://understat.com/league";
    private static final String[] LEAGUES = { "La_liga", "EPL", "Bundesliga", "Serie_A", "Ligue_1", "RFPL" };
    private static final String[] YEARS = { "2014", "2015", "2016", "2017", "2018", "2019" };
    private static final String TEAMS_DATA_STRING = "var teamsData = JSON.parse('";

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

    //    public void fill_team_history(JsonArray history)

    public void scrapeTeamsDataToJsonFile() {
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

        HtmlPage page = null;
        try {
            String url1 = BASE_URL + "/" + LEAGUES[0] + "/" + YEARS[0];
            for (String league : LEAGUES) {
                for (String year : YEARS) {
                    String url = BASE_URL + "/" + league + "/" + year;
                    String fileName = "resources/" + league + year;

                    page = client.getPage(url);
                    String data = StringEscapeUtils.unescapeJava(page
                                                                     .asXml()
                                                                     .replaceAll("\\\\x", "\\\\u00"));

                    int start = data.indexOf(TEAMS_DATA_STRING);
                    int end = data.indexOf("');");

                    final String substring = data.substring(start + TEAMS_DATA_STRING.length(), end);

                    JsonElement jsonElement = new JsonParser().parse(substring);
                    final JsonObject jsonObject = jsonElement.getAsJsonObject();

                    // write to a file
                    //                    PrintWriter out = new PrintWriter(fileName);
                    //                    out.println(jsonElement.toString());
                    //                    out.close();
                }
            }

            //            Pattern
            //                .compile(regex).matcher(str).replaceAll(repl)

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
