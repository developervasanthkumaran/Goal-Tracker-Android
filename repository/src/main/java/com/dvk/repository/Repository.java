package com.dvk.repository;


import com.dvk.datastore.api.OnGoalFetchedCallback;
import com.dvk.model.Event;
import com.dvk.model.Goal;

import java.util.List;

public interface Repository {
    void addGoal(Goal goal);
    void updateGoal(Goal goal);
    void deleteGoal(Goal goal);
    void fetchAllGoals(OnGoalFetchedCallback callback);
    void addEvent(Goal goal,Event event);
    void updateEventList(String statement,List<Event> event);
    void deleteEvent(Event event);
    void addListOfEvents(Goal goal,List<Event> eventList);
}
