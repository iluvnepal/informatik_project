////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 22.11.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thepanday.informatikproject.common.util.jsonentities.ScrapedPageContainer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

/**
 *
 */
public class MatchDataUtility {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchDataUtility.class);

    public static final String DEFAULT_JSON_DATA_FILE = "aggregated_json_data.json";
    public static final String DEFAULT_TRAINING_DATA = "default_training_data.txt";

    /**
     * Returns mapped TeamsContainer
     * @return
     */
    public static ScrapedPageContainer getTeamsContatinerFromLocalResources() {
        return jsonToTeamsDataContainer(getJsonStringFromFile(DEFAULT_JSON_DATA_FILE));
    }

    public static ScrapedPageContainer jsonToTeamsDataContainer(String jsonAsString) {
        var o = new ObjectMapper();

        try {
            return o.readValue(jsonAsString, ScrapedPageContainer.class);
        } catch (JsonProcessingException e) {
            LOGGER.error("Could not parse json string.");
            e.printStackTrace();
        }

        return null;
    }

    public static boolean appendJsonDataToFile(String jsonData, String fileName) {
        try {
            final BufferedWriter output = new BufferedWriter(new FileWriter(fileName, true));
            output.append(jsonData);
            output.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getJsonStringFromFile(String fileName) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(Paths
                                        .get(fileName)
                                        .toFile(), String.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Strings.EMPTY;
    }

}
