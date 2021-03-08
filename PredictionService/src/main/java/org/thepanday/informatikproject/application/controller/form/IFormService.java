////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 26.01.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.controller.form;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface IFormService {

    public Map<String, List<String>> getTeamsForAllLeague();

    public String[] getLeagues();
}


