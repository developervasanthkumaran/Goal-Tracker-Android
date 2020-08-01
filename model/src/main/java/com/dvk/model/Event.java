package com.dvk.model;

import java.io.Serializable;
import java.util.Calendar;


public class Event implements Serializable {
    public enum  status{ACTIVE, ENDED}
    private Calendar date;
    private Calendar time;
    private String description;
    private String notes;
    private status state;
    private long id;
        public Event(){}

    public Event(long id,String description){
        this.description = description;
        this.id=id;
    }

    public String getCurrentState(){
        if (state == status.ACTIVE) {
            return "ACTIVE";
        }
    return "ENDED";
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public status getState() {
        return state;
    }

    public void setState(status state) {
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

