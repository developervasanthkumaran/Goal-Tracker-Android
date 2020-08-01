package com.dvk.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.dvk.model.Event;

import java.util.Calendar;

public class EventViewModel extends AndroidViewModel {
    Event event;
    public EventViewModel(@NonNull Application application) {
        super(application);
        event = new Event();
    }

    public Event getEvent() {
        return event;
    }

    public void setDescription(String description) {
        this.event.setDescription(description);
    }
    public void setDate(Calendar calendar){
        this.event.setDate(calendar);
    }
    public void setTime(Calendar calendar){
        this.event.setTime(calendar);
    }
}
