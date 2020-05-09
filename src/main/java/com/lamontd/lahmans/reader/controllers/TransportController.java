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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamontd.lahmans.reader.model.Person;
import com.lamontd.lahmans.reader.model.Team;
import com.lamontd.lahmans.reader.repositories.CollegeStintRepository;
import com.lamontd.lahmans.reader.repositories.DivisionRepository;
import com.lamontd.lahmans.reader.repositories.FranchiseRepository;
import com.lamontd.lahmans.reader.repositories.LeagueRepository;
import com.lamontd.lahmans.reader.repositories.ManagerAwardRepository;
import com.lamontd.lahmans.reader.repositories.PersonRepository;
import com.lamontd.lahmans.reader.repositories.PlayerAppearanceRepository;
import com.lamontd.lahmans.reader.repositories.PlayerAwardRepository;
import com.lamontd.lahmans.reader.repositories.TeamRepository;
import com.lamontd.lahmans.reader.services.KafkaSender;
import com.lamontd.utils.jackson.JacksonMapper;
import com.lamontd.utils.model.MappedTransportObject;
import java.util.concurrent.atomic.LongAdder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
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
@RequestMapping(path = "/transport")
public class TransportController {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerAppearanceRepository playerAppearanceRepository;
    @Autowired
    private CollegeStintRepository collegeStintRepository;
    @Autowired
    private PlayerAwardRepository playerAwardRepository;
    @Autowired
    private ManagerAwardRepository managerAwardRepository;
    @Autowired
    private LeagueRepository leagueRepository;
    @Autowired
    private DivisionRepository divisionRepository;
    @Autowired
    private FranchiseRepository franchiseRepository;

    @Autowired
    private KafkaSender kafkaSender;

    private static final Log logger = LogFactory.getLog(TransportController.class);

    private boolean writeTransportObject(Object response) {
        final ObjectMapper outputMapper = JacksonMapper.getStandardMapper();
        MappedTransportObject transportObject = new MappedTransportObject(response);
        try {
            logger.info("TransportObject --> " + outputMapper.writeValueAsString(transportObject));
        } catch (JsonProcessingException ex) {
            logger.error("Problem converting to transport object", ex);
            return false;
        }
        kafkaSender.sendMessage(response);
        return true;
    }

    @GetMapping(path = "/person/{person-id}")
    public @ResponseBody
    UserResponse transportPersonInfo(@PathVariable("person-id") String personId,
            @RequestParam(value = "appearances", defaultValue = "false") boolean includeAppearances,
            @RequestParam(value = "colleges", defaultValue = "false") boolean includeColleges,
            @RequestParam(value = "awards", defaultValue = "false") boolean includeAwards) {
        final LongAdder responsesSent = new LongAdder();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("Player");
        logger.info("Doing transport lookup for PlayerID >" + personId + "<");
        Person player = personRepository.findByPlayerID(personId);
        UserResponse userResponse = new UserResponse();
        if (player == null) {
            userResponse.setSuccessful(false);
            userResponse.setComment("Person " + personId + " was not found");
            stopWatch.stop();
            userResponse.setStopWatch(stopWatch);
            userResponse.setResponsesSent(responsesSent.intValue());
            return userResponse;
        }

        writeTransportObject(player);
        responsesSent.increment();
        stopWatch.stop();
        if (includeColleges) {
            stopWatch.start("Colleges");
            collegeStintRepository.findByPlayerID(personId).forEach(stint -> {
                if (writeTransportObject(stint)) {
                    responsesSent.increment();
                }
            });
            stopWatch.stop();
        }
        if (includeAppearances) {
            stopWatch.start("Appearances");
            playerAppearanceRepository.findByPlayerID(personId).forEach(appearance -> {
                if (writeTransportObject(appearance)) {
                    responsesSent.increment();
                }
            });
            stopWatch.stop();
        }
        if (includeAwards) {
            stopWatch.start("Awards");
            playerAwardRepository.findByPlayerID(personId).forEach(award -> {
                if (writeTransportObject(award)) {
                    responsesSent.increment();
                }
            });
            managerAwardRepository.findByPlayerID(personId).forEach(award -> {
                if (writeTransportObject(award)) {
                    responsesSent.increment();
                }
            });
            stopWatch.stop();
        }
        return new UserResponse(responsesSent.intValue(), stopWatch);
    }

    @GetMapping(path = "/leagues")
    public @ResponseBody
    UserResponse transportLeagueInfo(@RequestParam(name = "divisions", defaultValue = "false") boolean includeDivisions) {
        final LongAdder responsesSent = new LongAdder();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("Leagues");
        leagueRepository.findAll().forEach((league -> {
            if (writeTransportObject(league)) {
                responsesSent.increment();
                if (includeDivisions) {
                    divisionRepository.findByLeagueID(league.getId()).forEach((division -> {
                        if (writeTransportObject(division)) {
                            responsesSent.increment();
                        }
                    }));
                }
            }
        }));
        stopWatch.stop();
        return new UserResponse(responsesSent.intValue(), stopWatch);
    }

    @GetMapping(path = "/team/id/{teamId}")
    public @ResponseBody
    UserResponse transportTeamInfo(@PathVariable(value = "teamId") Integer teamId) {
        final LongAdder responsesSent = new LongAdder();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("Team");
        Team daTeam = teamRepository.findByID(teamId);
        if (daTeam == null) {
            logger.warn("Could not find object with team " + teamId);
        }
        if (writeTransportObject(daTeam)) {
            responsesSent.increment();
        }
        stopWatch.stop();
        return new UserResponse(responsesSent.intValue(), stopWatch);
    }

    @GetMapping(path = "/team")
    public @ResponseBody
    UserResponse transportTeamsForRange(@RequestParam(name = "startYear") Short startYear,
            @RequestParam(name = "endYear") Short endYear) {
        final LongAdder responsesSent = new LongAdder();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("TeamsByRange");
        teamRepository.findByYearIDBetween(startYear, endYear).forEach(team -> {
            if (writeTransportObject(team)) {
                responsesSent.increment();
            }
        });
        stopWatch.stop();
        return new UserResponse(responsesSent.intValue(), stopWatch);
    }

    @GetMapping(path = "/franchises")
    public @ResponseBody
    UserResponse transportAllFranchises() {
        final LongAdder responsesSent = new LongAdder();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("Franchises");
        franchiseRepository.findAll().forEach(franchise -> {
            if (writeTransportObject(franchise)) {
                responsesSent.increment();
            }
        });
        stopWatch.stop();
        return new UserResponse(responsesSent.intValue(), stopWatch);
    }

}
