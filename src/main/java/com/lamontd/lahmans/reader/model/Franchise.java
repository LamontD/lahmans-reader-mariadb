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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author lamontdozierjr
 */
@Entity
@Table(name = "teamsfranchises")
public class Franchise {

    @Id
    @Column(name = "franchID")
    private String id;
    @Column(name = "franchName")
    private String name;
    @Column(columnDefinition = "char")
    private Boolean active;
    @Column(name = "NAassoc")
    private String nationalAssociationId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getNationalAssociationId() {
        return nationalAssociationId;
    }

    public void setNationalAssociationId(String nationalAssociationId) {
        this.nationalAssociationId = nationalAssociationId;
    }

}
