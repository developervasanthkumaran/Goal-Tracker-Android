package com.dvk.datastore;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.room.Room;

import com.dvk.datastore.Entity.EventEntity;
import com.dvk.datastore.Entity.GoalEntity;
import com.dvk.datastore.api.OnGoalFetchedCallback;
import com.dvk.datastore.api.Transaction;
import com.dvk.model.Event;
import com.dvk.model.Goal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RoomStorage implements Transaction {

    private final static String DB_NAME = "GTDatabase";
    static private DataBase database;
    private static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);
    public RoomStorage(Context context) {
        database = Room.databaseBuilder(context, DataBase.class, DB_NAME)
                .fallbackToDestructiveMigrationFrom(2,3)
                .build();
    }
    @Override
    public void addGoal(final Goal goal) {
        addGoalAsync(goal);
    }

    public static void addGoalAsync(final Goal goal) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                GoalEntity goalEntity = new GoalEntity();
                if (!goal.getStatement().isEmpty())
                    goalEntity.setStatement(goal.getStatement());
                database.goalDao().addGoal(goalEntity);
                return null;
            }
        }.execute();
    }

    @Override
    public void updateGoal(final Goal goal) {
        databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                GoalEntity goalEntity = new GoalEntity();
                goalEntity.setId(goal.getId());
                goalEntity.setStatement(goal.getStatement());
                database.goalDao().updateGoal(goalEntity);
            }
        });
    }

    @Override
    public void deleteGoal(final Goal goal) {

        deleteGoalAsync(goal);
    }

    public static void deleteGoalAsync(final Goal goal) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                GoalEntity goalEntity = new GoalEntity();
                goalEntity.setStatement(goal.getStatement());
                goalEntity.setId(database.goalDao().getGoal(goal.getStatement()).get(0).getId());
                database.goalDao().removeGoal(goalEntity);
                return null;
            }
        }.execute();
    }

    @Override
    public void fetchAllGoals(OnGoalFetchedCallback callback) {
        FetchGoals fetchGoalsTask = new FetchGoals(callback);
        fetchGoalsTask.execute();
    }

    public List<Event> EventEntityToEvent(final List<EventEntity> eventEntities) {
        final List<Event> eventList = new ArrayList<>();
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                for (EventEntity eventEntity : eventEntities) {
                    Event event = new Event();
                    event.setDescription(eventEntity.getDescription());
                    event.setDate(eventEntity.getDate());
                    event.setTime(eventEntity.getTime());
                    event.setNotes(eventEntity.getNotes());
                    String s = eventEntity.getDescription() + " " + eventEntity.getDate() + " " + eventEntity.getTime();
                    Log.i("event ", s);
                    eventList.add(event);
                }
            }
        });

        return eventList;
    }

    public List<EventEntity> getEventEntities(final String goalStatement) {
        final List<EventEntity> eventEntityList = new ArrayList<>();
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                final List<EventEntity> eventEntities =
                        database.eventDao().fetchAllEventEntitiesForGoal(goalStatement);
                eventEntityList.addAll(eventEntities);
            }
        });
        return eventEntityList;
    }

    @Override
    public void addEvent(final Goal goal, final Event event) {
        addEventAsync(goal, event);
    }

    public static void addEventAsync(final Goal goal, final Event event) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                EventEntity eventEntity = new EventEntity();
                if (!goal.getStatement().isEmpty())
                    eventEntity.setStatement(goal.getStatement());
                if (!event.getDescription().isEmpty())
                    eventEntity.setDescription(event.getDescription());
                if (event.getDate() != null)
                    eventEntity.setDate(event.getDate());
                if (event.getTime() != null)
                    eventEntity.setTime(event.getTime());
                database.eventDao().addEventEntity(eventEntity);
                return null;
            }
        }.execute();
    }

    @Override
    public void updateEventList(final String statement,final List<Event> events) {
        databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                for (Event event : events) {
                    EventEntity eventEntity = new EventEntity();
                    eventEntity.setStatement(statement);
                    eventEntity.setId(event.getId());
                    eventEntity.setDescription(event.getDescription());
                    eventEntity.setDate(event.getDate());
                    eventEntity.setTime(event.getTime());
                    eventEntity.setNotes(event.getNotes());
                    database.eventDao().updateEventEntity(eventEntity);
                }

            }
        });
    }

    @Override
    public void deleteEvent(final Event event) {
        deleteEventAsync(event);
    }

    public static void deleteEventAsync(final Event event){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                long id = database.eventDao().getEventId(event.getDescription());
                EventEntity eventEntity = new EventEntity();
                eventEntity.setDescription(event.getDescription());
                eventEntity.setDate(event.getDate());
                eventEntity.setTime(event.getTime());
                eventEntity.setNotes(event.getNotes());
                eventEntity.setId(id);
                database.eventDao().removeEvent(eventEntity.getDescription());
                return null;
            }
        }.execute();
    }

    @Override
    public void addListOfEvents(final Goal goal, final List<Event> eventList) {
        addListOfEventAsync(goal, eventList);
    }

    public static void addListOfEventAsync(final Goal goal, final List<Event> events) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
            database.eventDao().addEventEntityList(convertEventToEventEntity(goal,events));
                return null;
            }
        }.execute();
    }

    public static List<EventEntity> convertEventToEventEntity(Goal goal, List<Event> eventList) {
        List<EventEntity> eventEntities = new ArrayList<>();
        for (Event event : eventList) {
            EventEntity eventEntity = new EventEntity();
            if (!goal.getStatement().isEmpty())
                eventEntity.setStatement(goal.getStatement());
            if (!event.getDescription().isEmpty())
                eventEntity.setDescription(event.getDescription());
            if (event.getDate() != null)
                eventEntity.setDate(event.getDate());
            if (event.getTime() != null)
                eventEntity.setTime(event.getTime());
            if(event.getNotes()!=null)
                eventEntity.setNotes(event.getNotes());
            eventEntities.add(eventEntity);
        }
        return eventEntities;
    }

    private static class FetchGoals extends AsyncTask<Void, Void, List<Goal>> {

        private OnGoalFetchedCallback onGoalFetchedCallback;

        public FetchGoals(OnGoalFetchedCallback onGoalFetchedCallback) {
            this.onGoalFetchedCallback = onGoalFetchedCallback;
        }

        @Override
        protected List<Goal> doInBackground(Void... voids) {
            List<Goal> goalList = new ArrayList<>();
            List<GoalEntity> entities = database.goalDao().fetchAllGoals();
            for (GoalEntity goalEntity : entities) {
                final Goal goal = new Goal(goalEntity.getStatement());
                goal.setId(goalEntity.getId());
                Log.i("goal ", goalEntity.getStatement());
                final List<Event> eventList = new ArrayList<>();
                for (EventEntity eventEntity : database.eventDao().fetchAllEventEntitiesForGoal(goalEntity.getStatement())) {
                    Event event = new Event();
                    event.setId(eventEntity.getId());
                    event.setDescription(eventEntity.getDescription());
                    event.setDate(eventEntity.getDate());
                    event.setTime(eventEntity.getTime());
                    event.setNotes(eventEntity.getNotes());
                    String s = eventEntity.getDescription() + " " + eventEntity.getDate() + " " + eventEntity.getTime();
                    Log.i("event ", s);
                    eventList.add(event);
                }
                goal.setEventList(eventList);
                goalList.add(goal);
            }
            return goalList;
        }

        @Override
        protected void onPostExecute(List<Goal> goals) {
            super.onPostExecute(goals);
            onGoalFetchedCallback.onGoalFetched(goals);
        }
    }




}
