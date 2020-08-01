package com.dvk.datastore.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.dvk.datastore.tool.DateConverter;
import com.dvk.datastore.tool.TimeConverter;

import java.util.Calendar;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName ="event_table",indices =@Index(value = {"statement"})
        ,foreignKeys = @ForeignKey(entity = GoalEntity.class,parentColumns = {"statement"}
        ,childColumns = {"statement"}
        ,onDelete = CASCADE ))
public class EventEntity{

    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "statement")
    private String statement;

    @ColumnInfo(name = "description")
    private String description;

    @TypeConverters({DateConverter.class})
    @ColumnInfo(name = "scheduled_date")
    private Calendar date;

    @TypeConverters({TimeConverter.class})
    @ColumnInfo(name = "scheduled_time")
    private Calendar time;

    @ColumnInfo(name = "notes")
    private String notes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
