package org.thepanday.informatikproject.common.entity;

import java.util.ArrayList;
import java.util.List;

public class TeamData {
    public String mId;
    public String mTeamName;
    public List mTeamMatchesHistory = new ArrayList();

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

    public List getmTeamMatchesHistory() {
        return mTeamMatchesHistory;
    }

    public void setmTeamMatchesHistory(List mTeamMatchesHistory) {
        this.mTeamMatchesHistory = mTeamMatchesHistory;
    }

}
