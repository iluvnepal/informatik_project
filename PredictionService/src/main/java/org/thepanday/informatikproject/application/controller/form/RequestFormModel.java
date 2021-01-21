////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 17.11.2020.
//
// Copyright (c) 2006 - 2020 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.controller.form;

public class RequestFormModel {
    private String homeTeam;
    private String awayTeam;
    private String league;

    public String getHomeTeam() {return this.homeTeam;}

    public String getAwayTeam() {return this.awayTeam;}

    public String getLeague() {return this.league;}

    public void setHomeTeam(String homeTeam) {this.homeTeam = homeTeam; }

    public void setAwayTeam(String awayTeam) {this.awayTeam = awayTeam; }

    public void setLeague(String league) {this.league = league; }
}
