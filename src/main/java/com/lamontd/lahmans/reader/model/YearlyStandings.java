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

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author lamontdozierjr
 */
public class YearlyStandings {

    private Short year;
    private String league;
    private Character division;
    private Set<TeamRecord> teams;
    private static final Comparator<String> nullSafeStringComparator = Comparator.nullsFirst(String::compareToIgnoreCase);
    private static final Comparator<TeamRecord> recordComparator = Comparator
            .comparing(TeamRecord::getLeague, nullSafeStringComparator)
            .thenComparing(TeamRecord::getDivision, Comparator.nullsFirst(Character::compareTo))
            .thenComparing(TeamRecord::getWins, Comparator.nullsLast(Short::compareTo).reversed())
            .thenComparing(TeamRecord::getFranchise, nullSafeStringComparator);

    public YearlyStandings() {
        teams = new TreeSet<>(recordComparator);
    }

    public void addTeam(Team team) {
        this.teams.add(new TeamRecord(team));
    }

    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
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

    public Set<TeamRecord> getTeams() {
        return teams;
    }

    public void setTeams(Set<TeamRecord> teams) {
        this.teams = teams;
    }

}
