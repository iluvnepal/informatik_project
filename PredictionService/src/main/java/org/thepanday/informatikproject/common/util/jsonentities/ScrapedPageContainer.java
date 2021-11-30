////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 14.01.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.common.util.jsonentities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Set;

/**
 * Contains team
 */
public class ScrapedPageContainer {

    /**
     * Key is the team id.
     */
    private Map<String, TeamDetail> mTeamEntriesMap;

    @JsonCreator
    public ScrapedPageContainer(
        @JsonProperty()
        final Map<String, TeamDetail> mTeamEntriesMap) {
        this.mTeamEntriesMap = mTeamEntriesMap;
    }

    /**
     * Add entries from new container provided as parameter (likely from new page url) to pre-existing teamEntry.
     * @param container
     */
    public void addToEntries(ScrapedPageContainer container) {
        final Set<Map.Entry<String, TeamDetail>> teamEntrySet = container
            .getTeamEntriesMap()
            .entrySet();
        for (Map.Entry<String, TeamDetail> stringTeamDetailEntry : teamEntrySet) {
            final String teamId = stringTeamDetailEntry.getKey();
            if (mTeamEntriesMap.containsKey(teamId)) {
                final TeamDetail teamDetail = mTeamEntriesMap.get(teamId);
                teamDetail
                    .getHistory()
                    .addAll(stringTeamDetailEntry
                                .getValue()
                                .getHistory());
            } else {
                mTeamEntriesMap.putIfAbsent(teamId, stringTeamDetailEntry.getValue());
            }
        }
    }

    public Map<String, TeamDetail> getTeamEntriesMap() {return this.mTeamEntriesMap;}

    public void setTeamEntriesMap(Map<String, TeamDetail> teamEntriesMap) {this.mTeamEntriesMap = teamEntriesMap; }
}
