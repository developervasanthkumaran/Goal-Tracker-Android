package com.dvk.datastore;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.dvk.datastore.Dao.EventDao;
import com.dvk.datastore.Dao.GoalDao;
import com.dvk.datastore.Entity.EventEntity;
import com.dvk.datastore.Entity.GoalEntity;


@Database(entities = {GoalEntity.class,EventEntity.class}, version = 3, exportSchema = false)
abstract class DataBase extends RoomDatabase {
     abstract GoalDao goalDao();
     abstract EventDao eventDao();
}
