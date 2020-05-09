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

import com.lamontd.lahmans.reader.model.CollegeStint;
import com.lamontd.lahmans.reader.model.ManagerAward;
import com.lamontd.lahmans.reader.model.Person;
import com.lamontd.lahmans.reader.model.PlayerAppearance;
import com.lamontd.lahmans.reader.model.PlayerAward;
import com.lamontd.lahmans.reader.repositories.CollegeStintRepository;
import com.lamontd.lahmans.reader.repositories.ManagerAwardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lamontd.lahmans.reader.repositories.PersonRepository;
import com.lamontd.lahmans.reader.repositories.PlayerAppearanceRepository;
import com.lamontd.lahmans.reader.repositories.PlayerAwardRepository;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author lamontdozierjr
 */
@Controller
@RequestMapping(path = "/person")
public class PersonController {

    @Autowired
    private PersonRepository peopleRepository;
    @Autowired
    private PlayerAwardRepository playerAwardRepository;
    @Autowired
    private ManagerAwardRepository managerAwardRepository;
    @Autowired
    private PlayerAppearanceRepository playerAppearanceRepository;
    @Autowired
    private CollegeStintRepository collegeStintRepository;

    @GetMapping("/id/{playerid}")
    public @ResponseBody
    Person findById(@PathVariable("playerid") String playerId) {
        return peopleRepository.findByPlayerID(playerId);
    }

    @GetMapping("/id/{playerid}/appearances")
    public @ResponseBody
    Iterable<PlayerAppearance> findPlayerAppearancesById(@PathVariable("playerid") String playerId) {
        return playerAppearanceRepository.findByPlayerID(playerId);
    }

    @GetMapping("/id/{playerid}/appearances/team/{teamid}")
    public @ResponseBody
    Iterable<PlayerAppearance> findPlayerAppearancesByIdAndTeam(@PathVariable("playerid") String playerId, @PathVariable("teamid") String teamId) {
        return playerAppearanceRepository.findByPlayerIDAndTeamID(playerId, teamId);
    }

    @GetMapping("/id/{playerid}/colleges")
    public @ResponseBody
    Iterable<CollegeStint> findPlayerCollegeStintsById(@PathVariable("playerid") String playerId) {
        return collegeStintRepository.findByPlayerID(playerId);
    }

    @GetMapping("/id/{playerid}/playerawards")
    public @ResponseBody
    Iterable<PlayerAward> findPlayerAwardsById(@PathVariable("playerid") String playerId) {
        return playerAwardRepository.findByPlayerID(playerId);
    }

    @GetMapping("/id/{playerid}/managerawards")
    public @ResponseBody
    Iterable<ManagerAward> findManagerAwardsById(@PathVariable("playerid") String playerId) {
        return managerAwardRepository.findByPlayerID(playerId);
    }

    @GetMapping("/birthYear/{birthyear}")
    public @ResponseBody
    Iterable<Person> findByBirthYear(@PathVariable("birthyear") int birthYear) {
        return peopleRepository.findByBirthYear(birthYear);
    }

    @GetMapping("/counter/alive")
    public @ResponseBody
    int countAlivePeople() {
        int aliveCount = 0;
        Iterable<Person> alivePlayers = peopleRepository.findByDeathDateNull();
        for (Person p : alivePlayers) {
            aliveCount++;
        }
        return aliveCount;
    }

    @GetMapping("/counter/dead")
    public @ResponseBody
    int countDeadPeople() {
        int deadCount = 0;
        for (Person p : peopleRepository.findByDeathDateNotNull()) {
            deadCount++;
        }
        return deadCount;
    }

}
