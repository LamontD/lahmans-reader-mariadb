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
package com.lamontd.lahmans.reader.controllers;

import com.lamontd.lahmans.reader.model.EarnedAward;
import com.lamontd.lahmans.reader.model.Team;
import com.lamontd.lahmans.reader.model.TeamRecord;
import com.lamontd.lahmans.reader.model.YearlyStandings;
import com.lamontd.lahmans.reader.repositories.ManagerAwardRepository;
import com.lamontd.lahmans.reader.repositories.PlayerAwardRepository;
import com.lamontd.lahmans.reader.repositories.TeamRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author lamontdozierjr
 */
@Controller
@RequestMapping(path = "/standings")
public class YearlyStandingsController {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerAwardRepository playerAwardRepository;
    @Autowired
    private ManagerAwardRepository managerAwardRepository;

    @GetMapping(value = "/year/{year}")
    @ResponseBody
    public YearlyStandings getStandings(@PathVariable(value = "year") Short year,
            @RequestParam(value = "league", required = false) String league,
            @RequestParam(value = "division", required = false) Character division) {
        final YearlyStandings standings = new YearlyStandings();
        standings.setYear(year);
        standings.setLeague(league);
        standings.setDivision(division);
        Iterable<Team> teams = (league == null && division == null)
                ? teamRepository.findByYearID(year)
                : division == null ? teamRepository.findByYearIDAndLeague(year, league) : teamRepository.findByYearIDAndLeagueAndDivision(year, league, division);
        teams.forEach((team) -> standings.addTeam(team));
        return standings;
    }

    @GetMapping(value = "/year/{year}/champion/division")
    @ResponseBody
    public Iterable<TeamRecord> getDivisionChampions(@PathVariable(value = "year") Short year) {
        final List<TeamRecord> records = new ArrayList<>();
        teamRepository.findByYearIDAndDivisionWinnerTrue(year).forEach((team) -> records.add(new TeamRecord(team)));
        return records;
    }

    @GetMapping(value = "/year/{year}/champion/league")
    @ResponseBody
    public Iterable<TeamRecord> getLeagueChampions(@PathVariable(value = "year") Short year) {
        final List<TeamRecord> records = new ArrayList<>();
        teamRepository.findByYearIDAndLeagueChampionTrue(year).forEach((team) -> records.add(new TeamRecord(team)));
        return records;
    }

    @GetMapping(value = "/year/{year}/champion")
    @ResponseBody
    public Iterable<TeamRecord> getChampion(@PathVariable(value = "year") Short year) {
        final List<TeamRecord> records = new ArrayList<>();
        teamRepository.findByYearIDAndWorldSeriesChampionTrue(year).forEach((team) -> records.add(new TeamRecord(team)));
        return records;
    }

    @GetMapping(value = "/year/{year}/awards")
    @ResponseBody
    public Iterable<EarnedAward> getAwardsForYear(@PathVariable(value = "year") Short year) {
        final List<EarnedAward> awards = new ArrayList<>();
        playerAwardRepository.findByYearID(year).forEach(award -> awards.add(award));
        managerAwardRepository.findByYearID(year).forEach(award -> awards.add(award));
        return awards;
    }

    @GetMapping(value = "/franchise/{franchise}/champions")
    @ResponseBody
    public Iterable<TeamRecord> getFranchiseChampions(@PathVariable(value = "franchise") String franchise) {
        final List<TeamRecord> records = new ArrayList<>();
        teamRepository.findByFranchiseAndWorldSeriesChampionTrue(franchise).forEach(team -> records.add(new TeamRecord(team)));
        return records;
    }

    @GetMapping(value = "/franchise/{franchise}")
    @ResponseBody
    public Iterable<TeamRecord> getFranchiseStandingsBetween(@PathVariable(value = "franchise") String franchise,
            @RequestParam(name = "start") Short startYear, @RequestParam(name = "end") Short endYear) {
        final List<TeamRecord> records = new ArrayList();
        teamRepository.findByFranchiseAndYearIDBetween(franchise, startYear, endYear).forEach(team -> records.add(new TeamRecord(team)));
        return records;
    }
}
