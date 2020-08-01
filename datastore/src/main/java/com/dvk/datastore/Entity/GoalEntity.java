package com.dvk.datastore.Entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "goal_table",indices = {@Index(value = {"statement"},unique = true)})
public class GoalEntity  {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "statement")
    private String statement;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

}
