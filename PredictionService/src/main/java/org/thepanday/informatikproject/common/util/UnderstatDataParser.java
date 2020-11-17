////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 03.11.2020.
//
// Copyright (c) 2006 - 2020 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.common.util;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.thepanday.informatikproject.common.entity.TeamData;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 */
public class UnderstatDataParser {

    private static final String BASE_URL = "https://understat.com/league";
    private static final String[] LEAGUES = { "La_liga", "EPL", "Bundesliga", "Serie_A", "Ligue_1", "RFPL" };
    private static final String[] YEARS = { "2014", "2015", "2016", "2017", "2018", "2019" };
    private static final String TEAMS_DATA_STRING = "var teamsData = JSON.parse('";

    public void createTeamData() {
        try {
            String fromFile = Files.readString(Paths.get("try_data.txt"), StandardCharsets.US_ASCII);
            final ArrayList<TeamData> allTeamDataFromLeaugeYear = new ArrayList<>();
            JsonObject jsonObject = new JsonParser()
                .parse(fromFile)
                .getAsJsonObject();
            for (Map.Entry<String, JsonElement> stringJsonElementEntry : jsonObject.entrySet()) {
                final TeamData tmpTeamData = new TeamData();
                final JsonObject teamDataJsonObject = (JsonObject) stringJsonElementEntry.getValue();
                tmpTeamData.setmId(teamDataJsonObject
                                       .get("id")
                                       .toString());
                tmpTeamData.setmTeamName(teamDataJsonObject
                                             .get("title")
                                             .toString());
                JsonArray history = (JsonArray) teamDataJsonObject.get("history").getAsJsonArray();
                for (JsonElement jsonElement : history) {
                    System.out.println(jsonElement);
                }

                //todo : debug more

                System.out.println(stringJsonElementEntry.getKey());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void fill_team_history(JsonArray history)

    public void write_string_to_file(String filePath) {
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
            String url = BASE_URL + "/" + LEAGUES[0] + "/" + YEARS[0];
            page = client.getPage(url);
            String data = StringEscapeUtils.unescapeJava(page
                                                             .asXml()
                                                             .replaceAll("\\\\x", "\\\\u00"));

            int start = data.indexOf(TEAMS_DATA_STRING);
            int end = data.indexOf("');");

            final String substring = data.substring(start + TEAMS_DATA_STRING.length(), end);

            JsonElement jsonElement = new JsonParser().parse(substring);
            final JsonObject jsonObject = jsonElement.getAsJsonObject();

            PrintWriter out = new PrintWriter(filePath);
            out.println(jsonElement.toString());
            out.close();

            //            Pattern
            //                .compile(regex).matcher(str).replaceAll(repl)

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
