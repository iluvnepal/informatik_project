////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 14.01.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.common.entity.jsonentities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Set;

/**
 * Contains team
 */
public class TeamMatchesContainer {

    /**
     * Key is the team id.
     */
    private Map<String, TeamMatchesDetail> mTeamEntriesMap;

    @JsonCreator
    public TeamMatchesContainer(
        @JsonProperty()
        final Map<String, TeamMatchesDetail> mTeamEntriesMap) {
        this.mTeamEntriesMap = mTeamEntriesMap;
    }

    /**
     * Add entries from new container provided as parameter (likely from new page url) to pre-existing teamEntry.
     * @param container
     */
    public void addToEntries(TeamMatchesContainer container) {
        final Set<Map.Entry<String, TeamMatchesDetail>> teamEntrySet = container
            .getTeamEntriesMap()
            .entrySet();
        for (Map.Entry<String, TeamMatchesDetail> stringTeamDetailEntry : teamEntrySet) {
            final String teamId = stringTeamDetailEntry.getKey();
            if (mTeamEntriesMap.containsKey(teamId)) {
                final TeamMatchesDetail teamMatchesDetail = mTeamEntriesMap.get(teamId);
                teamMatchesDetail
                    .getHistory()
                    .addAll(stringTeamDetailEntry
                                .getValue()
                                .getHistory());
            } else {
                mTeamEntriesMap.putIfAbsent(teamId, stringTeamDetailEntry.getValue());
            }
        }
    }

    public Map<String, TeamMatchesDetail> getTeamEntriesMap() {return this.mTeamEntriesMap;}

    public TeamMatchesDetail getTeamDetailForTeam(String teamId) {
        return mTeamEntriesMap.get(teamId);
    }

    public void setTeamEntriesMap(Map<String, TeamMatchesDetail> teamEntriesMap) {this.mTeamEntriesMap = teamEntriesMap; }
}
