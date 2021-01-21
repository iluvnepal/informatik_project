////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 14.01.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.common.util.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 *
 */
public class EntryContainer {

    public Map<String, Entry> teamEntries;

    @JsonCreator
    public EntryContainer(
        @JsonProperty()
        final Map<String, Entry> teamEntries) {
        this.teamEntries = teamEntries;
    }
}
