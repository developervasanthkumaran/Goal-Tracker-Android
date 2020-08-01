package com.dvk.model;

import java.io.Serializable;
import java.util.List;

public class Goal implements Serializable {
    private String statement;
    private List<Event> eventList;
    private long id;
    public Goal(){}
    public Goal( String statement){
        this.statement = statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getStatement() {
        return statement;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public void addEvent(Event event){
        this.eventList.add(event);
    }

    public boolean removeEvent(int eventID){
        if(eventList.get(eventID)!=null) {
            eventList.remove(eventID);
            return true;
        }
        return false;
    }

    public String getEventState(int eId){
        return eventList.get(eId).getCurrentState();
    }

    public boolean containsEvent(Event event){
        return eventList.contains(event);
    }

    public boolean containsEvent(int id){
        return eventList.get(id)!=null;
    }

    public Event getEvent(int id){
        return eventList.get(id);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
