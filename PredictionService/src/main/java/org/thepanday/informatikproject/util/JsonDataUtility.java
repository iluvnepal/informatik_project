////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 22.11.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thepanday.informatikproject.common.entity.jsonentities.TeamDetailEntries;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 */
public class JsonDataUtility {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonDataUtility.class);

    public static final String DEFAULT_TRAINING_DATA = "default_training_data.txt";
    public static final String ALL_DATA = "json/default_team_entries.json";

    public static TeamDetailEntries teamDetailEntriesJsonToObject(String jsonAsString) {
        var o = new ObjectMapper();

        try {
            return o.readValue(jsonAsString, TeamDetailEntries.class);
        } catch (JsonProcessingException e) {
            LOGGER.error("Could not parse json string.");
            e.printStackTrace();
        }
        return null;
    }

    public static boolean teamDetailEntriesObjectToJsonFile(String fileName, TeamDetailEntries pageContainer) {
        var o = new ObjectMapper();
        try {
            final BufferedWriter output = new BufferedWriter(new FileWriter(fileName, false));
            o.writeValue(output, pageContainer);
            output.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Creates a file with given content.
     *
     * @param fileName
     * @param content
     * @return
     */
    public static boolean writeStringToFile(String fileName, String content) {
        try (BufferedWriter output = new BufferedWriter(new FileWriter(fileName, false))) {
            output.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static <T> T jsonToObject(String jsonString, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonString, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getStringFromFile(URL fileName) {
        try {
            return Files.readString(Path.of(fileName.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
            return Strings.EMPTY;
        }
    }

    public static String getStringFromFileInputStream(InputStream inputStream) {
        try (InputStreamReader streamReader = new InputStreamReader(inputStream); BufferedReader reader = new BufferedReader(streamReader)) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
