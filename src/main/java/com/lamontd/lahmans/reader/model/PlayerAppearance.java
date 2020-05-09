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
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author lamontdozierjr
 */
@Entity
@Table(name = "appearances")
public class PlayerAppearance {

    @Id
    private Integer ID;
    private Short yearID;
    @Column(columnDefinition = "char")
    private String teamID;
    @Column(name = "team_ID")
    private Integer teamUniqueID;
    private String playerID;
    @Column(name = "G_all")
    private Short totalGamesPlayed;
    @Column(name = "GS")
    private Short gamesStarted;
    @Column(name = "G_batting")
    private Short gamesBattedIn;
    @Column(name = "G_defense")
    private Short gamesAppearedOnDefense;
    @Column(name = "G_p")
    private Short gamesAsPitcher;
    @Column(name = "G_c")
    private Short gamesAsCatcher;
    @Column(name = "G_1b")
    private Short gamesAsFirstBaseman;
    @Column(name = "G_2b")
    private Short gamesAsSecondBaseman;
    @Column(name = "G_3b")
    private Short gamesAsThirdBaseman;
    @Column(name = "G_ss")
    private Short gamesAsShortStop;
    @Column(name = "G_lf")
    private Short gamesAsLeftFielder;
    @Column(name = "G_rf")
    private Short gamesAsRightFielder;
    @Column(name = "G_cf")
    private Short gamesAsCenterFielder;
    @Column(name = "G_of")
    private Short gamesAsOutfielder;
    @Column(name = "G_dh")
    private Short gamesAsDesignatedHitter;
    @Column(name = "G_ph")
    private Short gamesAsPinchHitter;
    @Column(name = "G_pr")
    private Short gamesAsPinchRunner;

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

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public Integer getTeamUniqueID() {
        return teamUniqueID;
    }

    public void setTeamUniqueID(Integer teamUniqueID) {
        this.teamUniqueID = teamUniqueID;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public Short getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public void setTotalGamesPlayed(Short totalGamesPlayed) {
        this.totalGamesPlayed = totalGamesPlayed;
    }

    public Short getGamesStarted() {
        return gamesStarted;
    }

    public void setGamesStarted(Short gamesStarted) {
        this.gamesStarted = gamesStarted;
    }

    public Short getGamesBattedIn() {
        return gamesBattedIn;
    }

    public void setGamesBattedIn(Short gamesBattedIn) {
        this.gamesBattedIn = gamesBattedIn;
    }

    public Short getGamesAppearedOnDefense() {
        return gamesAppearedOnDefense;
    }

    public void setGamesAppearedOnDefense(Short gamesAppearedOnDefense) {
        this.gamesAppearedOnDefense = gamesAppearedOnDefense;
    }

    public Short getGamesAsPitcher() {
        return gamesAsPitcher;
    }

    public void setGamesAsPitcher(Short gamesAsPitcher) {
        this.gamesAsPitcher = gamesAsPitcher;
    }

    public Short getGamesAsCatcher() {
        return gamesAsCatcher;
    }

    public void setGamesAsCatcher(Short gamesAsCatcher) {
        this.gamesAsCatcher = gamesAsCatcher;
    }

    public Short getGamesAsFirstBaseman() {
        return gamesAsFirstBaseman;
    }

    public void setGamesAsFirstBaseman(Short gamesAsFirstBaseman) {
        this.gamesAsFirstBaseman = gamesAsFirstBaseman;
    }

    public Short getGamesAsSecondBaseman() {
        return gamesAsSecondBaseman;
    }

    public void setGamesAsSecondBaseman(Short gamesAsSecondBaseman) {
        this.gamesAsSecondBaseman = gamesAsSecondBaseman;
    }

    public Short getGamesAsThirdBaseman() {
        return gamesAsThirdBaseman;
    }

    public void setGamesAsThirdBaseman(Short gamesAsThirdBaseman) {
        this.gamesAsThirdBaseman = gamesAsThirdBaseman;
    }

    public Short getGamesAsShortStop() {
        return gamesAsShortStop;
    }

    public void setGamesAsShortStop(Short gamesAsShortStop) {
        this.gamesAsShortStop = gamesAsShortStop;
    }

    public Short getGamesAsLeftFielder() {
        return gamesAsLeftFielder;
    }

    public void setGamesAsLeftFielder(Short gamesAsLeftFielder) {
        this.gamesAsLeftFielder = gamesAsLeftFielder;
    }

    public Short getGamesAsRightFielder() {
        return gamesAsRightFielder;
    }

    public void setGamesAsRightFielder(Short gamesAsRightFielder) {
        this.gamesAsRightFielder = gamesAsRightFielder;
    }

    public Short getGamesAsOutfielder() {
        return gamesAsOutfielder;
    }

    public void setGamesAsOutfielder(Short gamesAsOutfielder) {
        this.gamesAsOutfielder = gamesAsOutfielder;
    }

    public Short getGamesAsDesignatedHitter() {
        return gamesAsDesignatedHitter;
    }

    public void setGamesAsDesignatedHitter(Short gamesAsDesignatedHitter) {
        this.gamesAsDesignatedHitter = gamesAsDesignatedHitter;
    }

    public Short getGamesAsPinchHitter() {
        return gamesAsPinchHitter;
    }

    public void setGamesAsPinchHitter(Short gamesAsPinchHitter) {
        this.gamesAsPinchHitter = gamesAsPinchHitter;
    }

    public Short getGamesAsPinchRunner() {
        return gamesAsPinchRunner;
    }

    public void setGamesAsPinchRunner(Short gamesAsPinchRunner) {
        this.gamesAsPinchRunner = gamesAsPinchRunner;
    }

    public Short getGamesAsCenterFielder() {
        return gamesAsCenterFielder;
    }

    public void setGamesAsCenterFielder(Short gamesAsCenterFielder) {
        this.gamesAsCenterFielder = gamesAsCenterFielder;
    }

}
