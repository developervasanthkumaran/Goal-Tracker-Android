package com.dvk.datastore.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.dvk.datastore.Entity.GoalEntity;

import java.util.List;


@Dao
public abstract class GoalDao {

     @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void addGoal(GoalEntity... goalEntity);

     @Delete
   public abstract void removeGoal(GoalEntity...goalEntities);

   public abstract   @Query("select id from goal_table where statement = :statement")
     long getGoalId(String statement);

  public abstract  @Query("select * from goal_table where statement=:statement")
     List<GoalEntity> getGoal(String statement);

     @Transaction
   public void updateGoal(GoalEntity goalEntity){
         removeGoal(goalEntity);
         addGoal(goalEntity);
     }

     @Query("SELECT * FROM  goal_table ORDER BY id DESC")
   public abstract List<GoalEntity> fetchAllGoals();


}
