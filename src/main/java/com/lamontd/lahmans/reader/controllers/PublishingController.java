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
import com.lamontd.lahmans.reader.services.MappedTransportObjectKafkaSender;
import com.lamontd.transactionmanager.service.ComponentTransactionKafkaSender;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
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
@RequestMapping(path = "/publish")
@Tag(name = "Kafka Publisher",
        description = "Operations that GET data from the database and publish it to the output Kafka topic.")
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
    private MappedTransportObjectKafkaSender kafkaSender;
    @Autowired
    private ComponentTransactionKafkaSender transactionSender;

    private static final Log logger = LogFactory.getLog(PublishingController.class);

    @GetMapping(path = "/franchises")
    public @ResponseBody
    TransportStats transportAllFranchises() {
        final TransportStats transportStats = new TransportStats();
        Instant start = Instant.now();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("Franchises");
        franchiseRepository.findAll().forEach(franchise
                -> transportStats.addTransaction(sendAndCreateTransaction(franchise)));
        transportStats.setProcessingTime(Duration.between(start, Instant.now()));
        return transportStats;
    }

    @GetMapping(path = "/leagues")
    public @ResponseBody
    TransportStats transportLeagueInfo(@RequestParam(name = "divisions", defaultValue = "false") boolean includeDivisions) {
        final TransportStats transportStats = new TransportStats();
        Instant start = Instant.now();
        leagueRepository.findAll().forEach((league -> {
            transportStats.addTransaction(sendAndCreateTransaction(league));
            if (includeDivisions) {
                divisionRepository.findByLeagueID(league.getId()).forEach(division
                        -> transportStats.addTransaction(sendAndCreateTransaction(division)));
            }
        }));
        transportStats.setProcessingTime(Duration.between(start, Instant.now()));
        return transportStats;
    }

    @GetMapping(path = "/person/{person-id}")
    public @ResponseBody
    TransportStats transportIndividualPersonInfo(@PathVariable("person-id") String personId,
            @RequestParam(value = "includeAppearances", defaultValue = "false") boolean includeAppearances,
            @RequestParam(value = "includeColleges", defaultValue = "false") boolean includeColleges,
            @RequestParam(value = "includeAwards", defaultValue = "false") boolean includeAwards) {
        final TransportStats transportStats = new TransportStats();
        Instant start = Instant.now();
        logger.info("Doing transport lookup for PlayerID >" + personId + "<");
        Person player = personRepository.findByPlayerID(personId);
        if (player == null) {
            transportStats.setComment("Person " + personId + " was not found");
        } else {
            transportStats.setTransactions(publishPersonInfo(player, includeColleges, includeAppearances, includeAwards));
        }
        transportStats.setProcessingTime(Duration.between(start, Instant.now()));
        return transportStats;
    }

    @GetMapping(path = "/appearances/player/{player-id}")
    public @ResponseBody
    TransportStats transportAppearancesByPlayerId(@PathVariable(value = "player-id") String playerId,
            @RequestParam(value = "includeTeam", defaultValue = "false") boolean includeTeam) {
        final TransportStats transportStats = new TransportStats();
        Instant start = Instant.now();
        playerAppearanceRepository.findByPlayerID(playerId).forEach(appearance -> {
            transportStats.addTransaction(sendAndCreateTransaction(appearance));
            if (includeTeam) {
                Team team = teamRepository.findByID(appearance.getTeamUniqueID());
                transportStats.addTransaction(sendAndCreateTransaction(team));
            }
        });
        transportStats.setProcessingTime(Duration.between(start, Instant.now()));
        return transportStats;
    }

    @GetMapping(path = "/appearances/team/{team-id}")
    public @ResponseBody
    TransportStats transportAppearancesByTeamId(@PathVariable(value = "team-id") Integer teamUniqueId,
            @RequestParam(name = "includePlayers", defaultValue = "false") boolean includePlayers) {
        final TransportStats transportStats = new TransportStats();
        Instant start = Instant.now();
        playerAppearanceRepository.findByTeamUniqueID(teamUniqueId).forEach(appearance -> {
            transportStats.addTransaction(sendAndCreateTransaction(appearance));
            if (includePlayers) {
                Person player = personRepository.findById(appearance.getPlayerID()).get();
                transportStats.addTransaction(sendAndCreateTransaction(player));
            }
        });
        transportStats.setProcessingTime(Duration.between(start, Instant.now()));
        return transportStats;
    }

    @GetMapping(path = "/apperances/year/{year-id}")
    public @ResponseBody
    TransportStats transportAppearancesByYear(@PathVariable(value = "year-id") Short year,
            @RequestParam(name = "includeTeams", defaultValue = "false") boolean includeTeams,
            @RequestParam(name = "includePlayers", defaultValue = "false") boolean includePlayers) {
        final TransportStats transportStats = new TransportStats();
        Set<Integer> teamIds = new HashSet<>();
        Set<String> playerIds = new HashSet<>();
        Instant start = Instant.now();
        playerAppearanceRepository.findByYearID(year).forEach(appearance -> {
            transportStats.addTransaction(sendAndCreateTransaction(appearance));
            if (includeTeams) {
                teamIds.add(appearance.getTeamUniqueID());
            }
            if (includePlayers) {
                playerIds.add(appearance.getPlayerID());
            }
        });
        teamRepository.findByIDIn(teamIds).forEach(team
                -> transportStats.addTransaction(sendAndCreateTransaction(team)));
        personRepository.findByPlayerIDIn(playerIds).forEach(player
                -> transportStats.addTransaction(sendAndCreateTransaction(player)));
        transportStats.setProcessingTime(Duration.between(start, Instant.now()));
        return transportStats;
    }

    @GetMapping(path = "/team/id/{teamId}")
    public @ResponseBody
    TransportStats transportTeamInfo(@PathVariable(value = "teamId") Integer teamId) {
        final TransportStats transportStats = new TransportStats();
        Instant start = Instant.now();
        Team daTeam = teamRepository.findByID(teamId);
        if (daTeam == null) {
            logger.warn("Could not find object with team " + teamId);
        }
        transportStats.addTransaction(sendAndCreateTransaction(daTeam));
        transportStats.setProcessingTime(Duration.between(start, Instant.now()));
        return transportStats;
    }

    @GetMapping(path = "/team")
    public @ResponseBody
    TransportStats transportTeamsForYearRange(@RequestParam(name = "startYear") Short startYear,
            @RequestParam(name = "endYear") Short endYear) {
        final TransportStats transportStats = new TransportStats();
        Instant start = Instant.now();
        teamRepository.findByYearIDBetween(startYear, endYear).forEach(team
                -> transportStats.addTransaction(sendAndCreateTransaction(team))
        );
        transportStats.setProcessingTime(Duration.between(start, Instant.now()));
        return transportStats;
    }
    
    @GetMapping(path = "/all-data")
    public @ResponseBody
    TransportStats transportEntireYearInfo(@RequestParam(name="startYear") Short startYear,
            @RequestParam(name="endYear") Short endYear) {
        final TransportStats transportStats = new TransportStats();
        Instant start = Instant.now();
        // The year information includes: Teams
        teamRepository.findByYearIDBetween(startYear, endYear)
                .forEach(team -> transportStats.addTransaction(sendAndCreateTransaction(team)));
        // Appearances
        final Set<String> playerIDs = new TreeSet<>();
        playerAppearanceRepository.findByYearIDBetween(startYear, endYear).forEach(playerAppearance -> {
            transportStats.addTransaction(sendAndCreateTransaction(playerAppearance));
            playerIDs.add(playerAppearance.getPlayerID());
        });
        // Players (from the appearances)
        personRepository.findByPlayerIDIn(playerIDs);
        // Awards
        playerAwardRepository.findByYearIDBetween(startYear, endYear)
                .forEach(award -> transportStats.addTransaction(sendAndCreateTransaction(award)));
        transportStats.setProcessingTime(Duration.between(start, Instant.now()));
        transportStats.setCount(transportStats.getTransactions().size());
        return transportStats;
    }

    private List<String> publishPersonInfo(Person player, boolean includeColleges, boolean includeAppearances, boolean includeAwards) {
        List<String> transactionIds = new ArrayList<>();
        transactionIds.add(sendAndCreateTransaction(player));
        if (includeColleges) {
            collegeStintRepository.findByPlayerID(player.getPlayerID()).forEach(stint
                    -> transactionIds.add(sendAndCreateTransaction(stint)));
        }
        if (includeAppearances) {
            playerAppearanceRepository.findByPlayerID(player.getPlayerID()).forEach(appearance
                    -> transactionIds.add(sendAndCreateTransaction(appearance)));
        }
        if (includeAwards) {
            playerAwardRepository.findByPlayerID(player.getPlayerID()).forEach(award
                    -> transactionIds.add(sendAndCreateTransaction(award)));
            managerAwardRepository.findByPlayerID(player.getPlayerID()).forEach(award
                    -> transactionIds.add(sendAndCreateTransaction(award)));
        }
        return transactionIds;
    }
    
    private String sendAndCreateTransaction(Object output) {
        String transactionId = kafkaSender.send(output);
        transactionSender.publishInitial(transactionId);
        return transactionId;
    }

}
