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
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.concurrent.atomic.LongAdder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.util.StopWatch.TaskInfo;
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
@RequestMapping(path = "/publish")
@Tag(name = "Publishing Controller",
        description = "Queries data and publishes it to the underlying kafka topic.")
public class PublishingController {

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

    private static final Log logger = LogFactory.getLog(PublishingController.class);

    private boolean writeTransportObjectToKafka(Object response) {
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
    TransportStats transportPersonInfo(@PathVariable("person-id") String personId,
            @RequestParam(value = "appearances", defaultValue = "false") boolean includeAppearances,
            @RequestParam(value = "colleges", defaultValue = "false") boolean includeColleges,
            @RequestParam(value = "awards", defaultValue = "false") boolean includeAwards) {
        final LongAdder responsesSent = new LongAdder();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("Player");
        logger.info("Doing transport lookup for PlayerID >" + personId + "<");
        Person player = personRepository.findByPlayerID(personId);
        if (player == null) {
            stopWatch.stop();
            return new TransportStats("Person " + personId + " was not found");
        }

        writeTransportObjectToKafka(player);
        responsesSent.increment();
        if (includeColleges) {
            collegeStintRepository.findByPlayerID(personId).forEach(stint -> {
                if (writeTransportObjectToKafka(stint)) {
                    responsesSent.increment();
                }
            });
        }
        if (includeAppearances) {
            playerAppearanceRepository.findByPlayerID(personId).forEach(appearance -> {
                if (writeTransportObjectToKafka(appearance)) {
                    responsesSent.increment();
                }
            });
        }
        if (includeAwards) {
            playerAwardRepository.findByPlayerID(personId).forEach(award -> {
                if (writeTransportObjectToKafka(award)) {
                    responsesSent.increment();
                }
            });
            managerAwardRepository.findByPlayerID(personId).forEach(award -> {
                if (writeTransportObjectToKafka(award)) {
                    responsesSent.increment();
                }
            });
        }
        stopWatch.stop();
        return new TransportStats(responsesSent.intValue(), stopWatch);
    }

    @GetMapping(path = "/leagues")
    public @ResponseBody
    TransportStats transportLeagueInfo(@RequestParam(name = "divisions", defaultValue = "false") boolean includeDivisions) {
        final LongAdder responsesSent = new LongAdder();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("Leagues");
        leagueRepository.findAll().forEach((league -> {
            if (writeTransportObjectToKafka(league)) {
                responsesSent.increment();
                if (includeDivisions) {
                    divisionRepository.findByLeagueID(league.getId()).forEach((division -> {
                        if (writeTransportObjectToKafka(division)) {
                            responsesSent.increment();
                        }
                    }));
                }
            }
        }));
        stopWatch.stop();
        return new TransportStats(responsesSent.intValue(), stopWatch);
    }

    @GetMapping(path = "/team/id/{teamId}")
    public @ResponseBody
    TransportStats transportTeamInfo(@PathVariable(value = "teamId") Integer teamId) {
        final LongAdder responsesSent = new LongAdder();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("Team");
        Team daTeam = teamRepository.findByID(teamId);
        if (daTeam == null) {
            logger.warn("Could not find object with team " + teamId);
        }
        if (writeTransportObjectToKafka(daTeam)) {
            responsesSent.increment();
        }
        stopWatch.stop();
        return new TransportStats(responsesSent.intValue(), stopWatch);
    }

    @GetMapping(path = "/team")
    public @ResponseBody
    TransportStats transportTeamsForRange(@RequestParam(name = "startYear") Short startYear,
            @RequestParam(name = "endYear") Short endYear) {
        final LongAdder responsesSent = new LongAdder();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("TeamsByRange");
        teamRepository.findByYearIDBetween(startYear, endYear).forEach(team -> {
            if (writeTransportObjectToKafka(team)) {
                responsesSent.increment();
            }
        });
        stopWatch.stop();
        return new TransportStats(responsesSent.intValue(), stopWatch);
    }

    @GetMapping(path = "/franchises")
    public @ResponseBody
    TransportStats transportAllFranchises() {
        final LongAdder responsesSent = new LongAdder();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("Franchises");
        franchiseRepository.findAll().forEach(franchise -> {
            if (writeTransportObjectToKafka(franchise)) {
                responsesSent.increment();
            }
        });
        stopWatch.stop();
        return new TransportStats(responsesSent.intValue(), stopWatch);
    }

    public static final class TransportStats {

        private boolean success;
        private String comment;
        private int responsesSent;
        private long processingTimeMillis;

        public TransportStats(int responsesSent, StopWatch stopWatch) {
            this.responsesSent = responsesSent;
            this.success = true;
            for (TaskInfo taskInfo : stopWatch.getTaskInfo()) {
                this.processingTimeMillis += taskInfo.getTimeMillis();
            }
        }

        public TransportStats(String comment) {
            this.comment = comment;
            this.success = false;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getResponsesSent() {
            return responsesSent;
        }

        public void setResponsesSent(int responsesSent) {
            this.responsesSent = responsesSent;
        }

        public long getProcessingTimeMillis() {
            return processingTimeMillis;
        }

        public void setProcessingTimeMillis(long processingTimeMillis) {
            this.processingTimeMillis = processingTimeMillis;
        }

    }

}
