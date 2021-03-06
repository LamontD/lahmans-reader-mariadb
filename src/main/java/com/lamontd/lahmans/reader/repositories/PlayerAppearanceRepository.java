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

import com.lamontd.lahmans.reader.model.PlayerAppearance;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author lamontdozierjr
 */
public interface PlayerAppearanceRepository extends CrudRepository<PlayerAppearance, Integer> {
    PlayerAppearance findByID(Integer id);
    Iterable<PlayerAppearance> findByPlayerID(String playerID);
    Iterable<PlayerAppearance> findByTeamUniqueID(Integer teamUniqueID);
    Iterable<PlayerAppearance> findByTeamIDAndYearID(String teamID, Short year);
    Iterable<PlayerAppearance> findByPlayerIDAndTeamID(String playerID, String teamID);
    Iterable<PlayerAppearance> findByTeamIDAndYearIDBetween(String teamID, Short startYear, Short endYear);
    Iterable<PlayerAppearance> findByYearID(Short year);
    Iterable<PlayerAppearance> findByYearIDBetween(Short startYear, Short endYear);
    long countByPlayerID(String playerID);
    long countByPlayerIDAndTeamID(String playerID, String teamID);
    
}
