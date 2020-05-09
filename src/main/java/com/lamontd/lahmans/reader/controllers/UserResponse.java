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

import org.springframework.util.StopWatch;

/**
 *
 * @author lamontdozierjr
 */
public class UserResponse {

    private boolean successful;
    private String comment;
    private int responsesSent;
    private StopWatch stopWatch;

    public UserResponse() {
    }

    public UserResponse(int responsesSent, StopWatch stopWatch) {
        this.responsesSent = responsesSent;
        this.stopWatch = stopWatch;
        this.successful = true;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
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

    public StopWatch getStopWatch() {
        return stopWatch;
    }

    public void setStopWatch(StopWatch stopWatch) {
        this.stopWatch = stopWatch;
    }

}
