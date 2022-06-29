package com.rrd12.taskmaster.models;


import java.util.Date;


public class Task {


    public Long id;
    String title;
    String body;
    Date dateCreated;
    StateEnum state;


    public Task() {
    }

    public Task(String title) {
        this.title = title;
    }

    public Task(String title, String body, Date dateCreated, StateEnum state) {
        this.title = title;
        this.body = body;
        this.dateCreated = dateCreated;
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
