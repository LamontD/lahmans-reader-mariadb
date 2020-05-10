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

import com.lamontd.lahmans.reader.model.Team;
import com.lamontd.lahmans.reader.repositories.TeamRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author lamontdozierjr
 */
@Controller
@RequestMapping(path = "/team")
@Tag(name="Team Info",
        description="Provides access to team info, where a 'team' represents "
                + "a set of record and stats info for an individual franchise and year.")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @GetMapping("/id/{teamid}")
    public @ResponseBody
    Team findById(@PathVariable("teamid") Integer teamId) {
        return teamRepository.findByID(teamId);
    }

    @GetMapping("/year/{year}")
    public @ResponseBody
    Iterable<Team> findByYear(@PathVariable("year") Short year) {
        return teamRepository.findByYearID(year);
    }

    @GetMapping("/franchise/{franchise_id}/{year}")
    public @ResponseBody
    Iterable<Team> findFranchiseByYear(@PathVariable("franchise_id") String franchise,
            @PathVariable("year") Short year) {
        return teamRepository.findByYearIDAndFranchise(year, franchise);
    }

}
