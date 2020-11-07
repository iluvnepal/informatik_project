package common.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TeamData {
    public String mId;
    public String mTeamName;
    public List<MatchHistory> mTeamMatchesHistory = new ArrayList();

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmTeamName() {
        return mTeamName;
    }

    public void setmTeamName(String mTeamName) {
        this.mTeamName = mTeamName;
    }

    public List<MatchHistory> getmTeamMatchesHistory() {
        return mTeamMatchesHistory;
    }

    public void setmTeamMatchesHistory(List<MatchHistory> mTeamMatchesHistory) {
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
