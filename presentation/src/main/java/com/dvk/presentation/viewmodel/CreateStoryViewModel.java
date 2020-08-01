package com.dvk.presentation.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.dvk.model.Event;
import com.dvk.model.Goal;
import com.dvk.repository.Repository;

import java.util.ArrayList;
import java.util.List;


public class CreateStoryViewModel extends AndroidViewModel {
    Goal goal;
    List<Event> events;
    Repository repository;
    public CreateStoryViewModel(@NonNull Application application, Repository repository) {
        super(application);
        this.repository=repository;
        events = new ArrayList<>();
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void setGoal(String title) {
        this.goal = new Goal(title);
    }

    public void removeEvent(Event event){
        repository.deleteEvent(event);
    }
    public void insertStoryToRepository(){
        repository.addGoal(goal);
        repository.addListOfEvents(goal,events);
    }

    public Goal getGoal() {
        return goal;
    }
}
