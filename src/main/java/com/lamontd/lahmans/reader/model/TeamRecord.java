/*
 * Copyright (C) 2020 lamontdozierjr
 *
 * This program is licensed under a Creative Commons Attribution-Share 
 * Alike 3.0 Unported License ("CC-BY-SA"). An explanation of CC-BY-SA can 
 * be found at:
 *
 * <http://creativecommons.org/license/by-sa/3.0/>.
 *
 * Unless required by applicable law or agreed to in writing, this software
 * is distributed on an "AS IS" BASIS, WITHOUT ANY WARRANTY or even 
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 */
package com.lamontd.lahmans.reader.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author lamontdozierjr
 */
public final class TeamRecord {

    public static final String DIVISION_CHAMP = "Division Champion";
    public static final String WORLD_SERIES_CHAMP = "World Series Champion";
    private Integer teamID;
    private String league;
    private Character division;
    private String name;
    private String franchise;
    private Short games;
    private Short wins;
    private Short losses;
    private Short teamRank;
    private List<String> honors;

    public TeamRecord() {
    }

    public TeamRecord(Team team) {
        this.teamID = team.getID();
        this.league = team.getLeague();
        this.division = team.getDivision();
        this.name = team.getName();
        this.franchise = team.getFranchise();
        this.games = team.getGamesPlayed();
        this.wins = team.getWins();
        this.losses = team.getLosses();
        this.teamRank = team.getTeamRank();
        this.honors = new ArrayList<>();
        if (team.getDivisionWinner() != null && team.getDivisionWinner()) {
            this.honors.add(DIVISION_CHAMP);
        }
        if (team.getLeagueChampion() != null && team.getLeagueChampion()) {
            this.honors.add(StringUtils.defaultString(team.getLeague(), "League") + " Champion");
        }
        if (team.getWorldSeriesChampion() != null && team.getWorldSeriesChampion()) {
            this.honors.add(WORLD_SERIES_CHAMP);
        }
        if (this.honors.isEmpty()) {
            this.honors = null;
        }
    }

    public Integer getTeamID() {
        return teamID;
    }

    public void setTeamID(Integer teamID) {
        this.teamID = teamID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFranchise() {
        return franchise;
    }

    public void setFranchise(String franchise) {
        this.franchise = franchise;
    }

    public Short getGames() {
        return games;
    }

    public void setGames(Short games) {
        this.games = games;
    }

    public Short getWins() {
        return wins;
    }

    public void setWins(Short wins) {
        this.wins = wins;
    }

    public Short getLosses() {
        return losses;
    }

    public void setLosses(Short losses) {
        this.losses = losses;
    }

    public List<String> getHonors() {
        return honors;
    }

    public void setHonors(List<String> honors) {
        this.honors = honors;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public Character getDivision() {
        return division;
    }

    public void setDivision(Character division) {
        this.division = division;
    }

    public Short getTeamRank() {
        return teamRank;
    }

    public void setTeamRank(Short teamRank) {
        this.teamRank = teamRank;
    }

    
}
