package common.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Contains all the match data for a team.
 */
public class TeamData {
    public String mId;
    public String mTeamName;
    public List<MatchHistory> mTeamMatchesHistory = new ArrayList();

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        if (mId.startsWith("\"")) {
            mId = mId.substring(1, mId.length() - 1);
        }
        this.mId = mId;
    }

    public String getTeamName() {
        return mTeamName;
    }

    public void setTeamName(String mTeamName) {
        if (mTeamName.startsWith("\"")) {
            mTeamName = mTeamName.substring(1, mTeamName.length() - 1);
        }
        this.mTeamName = mTeamName;
    }

    public List<MatchHistory> getTeamMatchesHistory() {
        return mTeamMatchesHistory;
    }

    public void setTeamMatchesHistory(List<MatchHistory> mTeamMatchesHistory) {
        this.mTeamMatchesHistory.addAll(mTeamMatchesHistory);
    }

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
