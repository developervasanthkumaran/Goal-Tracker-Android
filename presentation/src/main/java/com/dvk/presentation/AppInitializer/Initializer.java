package com.dvk.presentation.AppInitializer;

import android.app.Application;

import com.dvk.datastore.RoomStorage;
import com.dvk.datastore.api.Transaction;
import com.dvk.repository.Repo;
import com.dvk.repository.Repository;


public class Initializer extends Application {

    private  Repository repository;

    @Override
    public void onCreate() {
        super.onCreate();
        Transaction transaction = new RoomStorage(this);
       repository = new Repo(transaction);
    }

    public Repository getRepository() {
        return this.repository;
    }
}
