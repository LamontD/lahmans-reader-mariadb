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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author lamontdozierjr
 */
@Entity
@Table(name = "teams")
public class Team {

    @Id
    private Integer ID;
    private Short yearID;
    @Column(name = "lgID", columnDefinition = "char")
    private String league;
    @Column(columnDefinition = "char")
    private String teamID;
    @Column(name = "franchID")
    private String franchise;
    @Column(name = "divID")
    private Character division;
    @Column(name = "div_ID")
    private Integer uniqueDivisionID;
    private Short teamRank;
    @Column(name = "G")
    private Short gamesPlayed;
    @Column(name = "GHome")
    private Short homeGames;
    @Column(name = "W")
    private Short wins;
    @Column(name = "L")
    private Short losses;
    @Column(name = "DivWin")
    private Boolean divisionWinner;
    @Column(name = "WCWin")
    private Boolean wildcardWinner;
    @Column(name = "LgWin")
    private Boolean leagueChampion;
    @Column(name = "WSWin")
    private Boolean worldSeriesChampion;
    @Column(name = "R")
    private Short runsScored;
    @Column(name = "AB")
    private Short atBats;
    @Column(name = "H")
    private Short hits;
    @Column(name = "2B")
    private Short doubles;
    @Column(name = "3B")
    private Short triples;
    @Column(name = "HR")
    private Short homeRuns;
    @Column(name = "BB")
    private Short walks;
    @Column(name = "SO")
    private Short strikeoutsByBatters;
    @Column(name = "SB")
    private Short stolenBases;
    @Column(name = "CS")
    private Short caughtStealing;

    @Column(name = "HBP")
    private Short battersHitByPitch;
    @Column(name = "SF")
    private Short sacrificeFlies;
    @Column(name = "RA")
    private Short opponentsRunsScored;
    @Column(name = "ER")
    private Short earnedRunsAllowed;
    @Column(name = "ERA")
    private Double earnedRunAverage;
    @Column(name = "CG")
    private Short completeGames;
    @Column(name = "SHO")
    private Short shutouts;
    @Column(name = "SV")
    private Short saves;
    @Column(name = "IPOuts")
    private Integer outsPitched;
    @Column(name = "HA")
    private Short hitsAllowed;
    @Column(name = "HRA")
    private Short homerunsAllowed;
    @Column(name = "BBA")
    private Short walksAllowed;
    @Column(name = "SOA")
    private Short strikeoutsByPitchers;
    @Column(name = "E")
    private Integer errors;
    @Column(name = "DP")
    private Integer doublePlays;
    @Column(name = "FP")
    private Double fieldingPercentage;
    private String name;
    private String park;
    private Integer attendance;
    @Column(name = "BPF")
    private Integer battersParkFactor;
    @Column(name = "PPF")
    private Integer pitchersParkFactor;
    @Column(name = "teamIDBR")
    private String baseballReferenceTeamID;
    @Column(name = "teamIDlahman45")
    private String lahman45TeamID;
    @Column(name = "teamIDretro")
    private String retrosheetTeamID;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Short getYearID() {
        return yearID;
    }

    public void setYearID(Short yearID) {
        this.yearID = yearID;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public String getFranchise() {
        return franchise;
    }

    public void setFranchise(String franchise) {
        this.franchise = franchise;
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

    public Short getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(Short gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public Short getHomeGames() {
        return homeGames;
    }

    public void setHomeGames(Short homeGames) {
        this.homeGames = homeGames;
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

    public Boolean getDivisionWinner() {
        return divisionWinner;
    }

    public void setDivisionWinner(Boolean divisionWinner) {
        this.divisionWinner = divisionWinner;
    }

    public Boolean getWildcardWinner() {
        return wildcardWinner;
    }

    public void setWildcardWinner(Boolean wildcardWinner) {
        this.wildcardWinner = wildcardWinner;
    }

    public Boolean getLeagueChampion() {
        return leagueChampion;
    }

    public void setLeagueChampion(Boolean leagueChampion) {
        this.leagueChampion = leagueChampion;
    }

    public Boolean getWorldSeriesChampion() {
        return worldSeriesChampion;
    }

    public void setWorldSeriesChampion(Boolean worldSeriesChampion) {
        this.worldSeriesChampion = worldSeriesChampion;
    }

    public Short getRunsScored() {
        return runsScored;
    }

    public void setRunsScored(Short runsScored) {
        this.runsScored = runsScored;
    }

    public Short getAtBats() {
        return atBats;
    }

    public void setAtBats(Short atBats) {
        this.atBats = atBats;
    }

    public Short getHits() {
        return hits;
    }

    public void setHits(Short hits) {
        this.hits = hits;
    }

    public Short getDoubles() {
        return doubles;
    }

    public void setDoubles(Short doubles) {
        this.doubles = doubles;
    }

    public Short getTriples() {
        return triples;
    }

    public void setTriples(Short triples) {
        this.triples = triples;
    }

    public Short getHomeRuns() {
        return homeRuns;
    }

    public void setHomeRuns(Short homeRuns) {
        this.homeRuns = homeRuns;
    }

    public Short getWalks() {
        return walks;
    }

    public void setWalks(Short walks) {
        this.walks = walks;
    }

    public Short getStrikeoutsByBatters() {
        return strikeoutsByBatters;
    }

    public void setStrikeoutsByBatters(Short strikeoutsByBatters) {
        this.strikeoutsByBatters = strikeoutsByBatters;
    }

    public Short getStolenBases() {
        return stolenBases;
    }

    public void setStolenBases(Short stolenBases) {
        this.stolenBases = stolenBases;
    }

    public Short getCaughtStealing() {
        return caughtStealing;
    }

    public void setCaughtStealing(Short caughtStealing) {
        this.caughtStealing = caughtStealing;
    }

    public Short getBattersHitByPitch() {
        return battersHitByPitch;
    }

    public void setBattersHitByPitch(Short battersHitByPitch) {
        this.battersHitByPitch = battersHitByPitch;
    }

    public Short getSacrificeFlies() {
        return sacrificeFlies;
    }

    public void setSacrificeFlies(Short sacrificeFlies) {
        this.sacrificeFlies = sacrificeFlies;
    }

    public Short getOpponentsRunsScored() {
        return opponentsRunsScored;
    }

    public void setOpponentsRunsScored(Short opponentsRunsScored) {
        this.opponentsRunsScored = opponentsRunsScored;
    }

    public Short getEarnedRunsAllowed() {
        return earnedRunsAllowed;
    }

    public void setEarnedRunsAllowed(Short earnedRunsAllowed) {
        this.earnedRunsAllowed = earnedRunsAllowed;
    }

    public Double getEarnedRunAverage() {
        return earnedRunAverage;
    }

    public void setEarnedRunAverage(Double earnedRunAverage) {
        this.earnedRunAverage = earnedRunAverage;
    }

    public Short getCompleteGames() {
        return completeGames;
    }

    public void setCompleteGames(Short completeGames) {
        this.completeGames = completeGames;
    }

    public Short getShutouts() {
        return shutouts;
    }

    public void setShutouts(Short shutouts) {
        this.shutouts = shutouts;
    }

    public Short getSaves() {
        return saves;
    }

    public void setSaves(Short saves) {
        this.saves = saves;
    }

    public Integer getOutsPitched() {
        return outsPitched;
    }

    public void setOutsPitched(Integer outsPitched) {
        this.outsPitched = outsPitched;
    }

    public Short getHitsAllowed() {
        return hitsAllowed;
    }

    public void setHitsAllowed(Short hitsAllowed) {
        this.hitsAllowed = hitsAllowed;
    }

    public Short getHomerunsAllowed() {
        return homerunsAllowed;
    }

    public void setHomerunsAllowed(Short homerunsAllowed) {
        this.homerunsAllowed = homerunsAllowed;
    }

    public Short getWalksAllowed() {
        return walksAllowed;
    }

    public void setWalksAllowed(Short walksAllowed) {
        this.walksAllowed = walksAllowed;
    }

    public Short getStrikeoutsByPitchers() {
        return strikeoutsByPitchers;
    }

    public void setStrikeoutsByPitchers(Short strikeoutsByPitchers) {
        this.strikeoutsByPitchers = strikeoutsByPitchers;
    }

    public Integer getErrors() {
        return errors;
    }

    public void setErrors(Integer errors) {
        this.errors = errors;
    }

    public Integer getDoublePlays() {
        return doublePlays;
    }

    public void setDoublePlays(Integer doublePlays) {
        this.doublePlays = doublePlays;
    }

    public Double getFieldingPercentage() {
        return fieldingPercentage;
    }

    public void setFieldingPercentage(Double fieldingPercentage) {
        this.fieldingPercentage = fieldingPercentage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPark() {
        return park;
    }

    public void setPark(String park) {
        this.park = park;
    }

    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }

    public Integer getBattersParkFactor() {
        return battersParkFactor;
    }

    public void setBattersParkFactor(Integer battersParkFactor) {
        this.battersParkFactor = battersParkFactor;
    }

    public Integer getPitchersParkFactor() {
        return pitchersParkFactor;
    }

    public void setPitchersParkFactor(Integer pitchersParkFactor) {
        this.pitchersParkFactor = pitchersParkFactor;
    }

    public String getBaseballReferenceTeamID() {
        return baseballReferenceTeamID;
    }

    public void setBaseballReferenceTeamID(String baseballReferenceTeamID) {
        this.baseballReferenceTeamID = baseballReferenceTeamID;
    }

    public String getLahman45TeamID() {
        return lahman45TeamID;
    }

    public void setLahman45TeamID(String lahman45TeamID) {
        this.lahman45TeamID = lahman45TeamID;
    }

    public String getRetrosheetTeamID() {
        return retrosheetTeamID;
    }

    public void setRetrosheetTeamID(String retrosheetTeamID) {
        this.retrosheetTeamID = retrosheetTeamID;
    }

}
