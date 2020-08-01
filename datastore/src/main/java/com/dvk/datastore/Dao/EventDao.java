package com.dvk.datastore.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.dvk.datastore.Entity.EventEntity;

import java.util.List;

@Dao
public abstract class EventDao {

    @Insert
   public abstract void addEventEntity(EventEntity... eventEntity);

    @Insert
    public abstract void addEventEntityList(List<EventEntity> eventEntity);

    @Transaction
    public  void updateEventEntity(EventEntity eventEntities){
       removeEvent(eventEntities.getDescription());
       addEventEntity(eventEntities);
    }

    @Query("DELETE FROM event_table WHERE description=:eventEntity")
    public abstract void removeEvent(String eventEntity);

    @Query("SELECT * FROM event_table WHERE description=:description")
    public abstract  List<EventEntity> getEvent(String description);

    @Query("SELECT id FROM event_table WHERE description = :description")
    public abstract  long getEventId(String description);

    @Query("SELECT * FROM event_table WHERE statement=:st")
    public abstract List<EventEntity> fetchAllEventEntitiesForGoal(String st);

}
