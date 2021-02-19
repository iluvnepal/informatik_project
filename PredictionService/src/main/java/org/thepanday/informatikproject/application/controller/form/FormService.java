////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 26.01.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.controller.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thepanday.informatikproject.common.util.UnderstatDataParser;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class FormService implements IFormService {

    @Autowired
    private UnderstatDataParser dataParser;

    @Override
    public Map<String, List<String>> getTeamsForAllLeague() {
        if (dataParser
            .getLeagueTeamsMap()
            .isEmpty()) {
            dataParser.populateLeagueTeamsMap();
        }
        return dataParser.getLeagueTeamsMap();
    }

    @Override
    public String[] getLeagues() {
        return dataParser.getLeagues();
    }

}
