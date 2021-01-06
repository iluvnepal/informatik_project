package org.thepanday.informatikproject.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Accessors(prefix = "m")
@Getter
@Setter
public class TeamData {
    public String mId;
    public String mTeamName;
    public List<MatchHistory> mTeamMatchesHistory = new ArrayList();

    public void addMatchHistory(MatchHistory matchHistory) {
        if (!mTeamMatchesHistory.contains(matchHistory)) {
            mTeamMatchesHistory.add(matchHistory);
        } else {
            Logger
                .getGlobal()
                .info("history already available.");
        }
    }
}
