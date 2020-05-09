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

import com.lamontd.lahmans.reader.model.ManagerAward;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author lamontdozierjr
 */
public interface ManagerAwardRepository extends CrudRepository<ManagerAward, Integer> {
    ManagerAward findByID(Integer id);
    Iterable<ManagerAward> findByYearID(Short year);
    Iterable<ManagerAward> findByPlayerID(String playerID);
    
}