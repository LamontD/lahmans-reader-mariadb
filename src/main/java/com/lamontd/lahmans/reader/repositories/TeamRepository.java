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
package com.lamontd.lahmans.reader.repositories;

import com.lamontd.lahmans.reader.model.Team;
import java.util.Collection;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author lamontdozierjr
 */
public interface TeamRepository extends CrudRepository<Team, Integer> {

    Team findByID(Integer teamID);

    Iterable<Team> findByYearID(Short yearID);

    Iterable<Team> findByYearIDAndFranchise(Short yearID, String franchise);

    Iterable<Team> findByYearIDAndLeague(Short yearID, String league);

    Iterable<Team> findByYearIDAndLeagueAndDivision(Short yearID, String league, Character division);

    Iterable<Team> findByYearIDAndWorldSeriesChampionTrue(Short yearID);

    Iterable<Team> findByYearIDAndLeagueAndLeagueChampionTrue(Short yearID, String league);

    Iterable<Team> findByYearIDAndLeagueAndDivisionAndDivisionWinnerTrue(Short yearID, String league, Character division);

    Iterable<Team> findByYearIDAndLeagueChampionTrue(Short yearID);

    Iterable<Team> findByYearIDAndDivisionWinnerTrue(Short yearID);

    Iterable<Team> findByFranchiseAndWorldSeriesChampionTrue(String franchise);

    Iterable<Team> findByFranchiseAndYearIDBetween(String franchise, Short startYear, Short endYear);

    Iterable<Team> findByYearIDBetween(Short startYear, Short endYear);
    
    Iterable<Team> findByIDIn(Collection<Integer> idList);
}
