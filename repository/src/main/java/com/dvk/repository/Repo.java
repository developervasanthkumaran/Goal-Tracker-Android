package com.dvk.repository;


import com.dvk.datastore.api.OnGoalFetchedCallback;
import com.dvk.datastore.api.Transaction;
import com.dvk.model.Event;
import com.dvk.model.Goal;

import java.util.List;

public class Repo implements Repository{

    private Transaction transaction;

    public Repo(Transaction transaction){
        this.transaction = transaction;
    }


    @Override
    public void addGoal(Goal goal) {
        transaction.addGoal(goal);
    }

    @Override
    public void updateGoal(Goal goal) {
            transaction.updateGoal(goal);
    }

    @Override
    public void deleteGoal(Goal goal) {
            transaction.deleteGoal(goal);
    }

    @Override
    public void fetchAllGoals(OnGoalFetchedCallback callback) {
        transaction.fetchAllGoals(callback);
    }

    @Override
    public void addEvent(Goal goal,Event event) {
        transaction.addEvent(goal,event);
    }

    @Override
    public void updateEventList(String statement,List<Event> event) {
        transaction.updateEventList(statement,event);
    }

    @Override
    public void deleteEvent(Event event) {
        transaction.deleteEvent(event);
    }

    @Override
    public void addListOfEvents(Goal goal,List<Event> eventList) {
        transaction.addListOfEvents(goal,eventList);
    }
}