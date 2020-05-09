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

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author lamontdozierjr
 */
@Entity
@Table(name = "collegeplaying")
public class CollegeStint {

    @Id
    private Integer ID;
    private String playerID;
//    private String schoolID;
    private Short yearID;

    @ManyToOne()
    @JoinColumn(name = "schoolID")
    private School school;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

//    public String getSchoolID() {
//        return schoolID;
//    }
//
//    public void setSchoolID(String schoolID) {
//        this.schoolID = schoolID;
//    }
    public Short getYearID() {
        return yearID;
    }

    public void setYearID(Short yearID) {
        this.yearID = yearID;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

}
