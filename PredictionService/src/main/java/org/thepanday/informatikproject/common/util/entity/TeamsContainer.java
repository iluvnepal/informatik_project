////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 14.01.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.common.util.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Set;

/**
 * Contains team
 */
public class TeamsContainer {

    public Map<String, TeamDetail> teamEntries;

    @JsonCreator
    public TeamsContainer(
        @JsonProperty()
        final Map<String, TeamDetail> teamEntries) {
        this.teamEntries = teamEntries;
    }

    /**
     * Add entries from new container provided as parameter (likely from new page url) to pre-existing teamEntry.
     * @param container
     */
    public void addToEntries(TeamsContainer container) {
        final Set<Map.Entry<String, TeamDetail>> teamEntrySet = container
            .getTeamEntries()
            .entrySet();
        for (Map.Entry<String, TeamDetail> stringTeamDetailEntry : teamEntrySet) {
            final String teamId = stringTeamDetailEntry.getKey();
            if (teamEntries.containsKey(teamId)) {
                final TeamDetail teamDetail = teamEntries.get(teamId);
                teamDetail
                    .getHistory()
                    .addAll(stringTeamDetailEntry
                                .getValue()
                                .getHistory());
            } else {
                teamEntries.putIfAbsent(teamId, stringTeamDetailEntry.getValue());
            }
        }

    }

    public Map<String, TeamDetail> getTeamEntries() {return this.teamEntries;}

    public void setTeamEntries(Map<String, TeamDetail> teamEntries) {this.teamEntries = teamEntries; }
}
