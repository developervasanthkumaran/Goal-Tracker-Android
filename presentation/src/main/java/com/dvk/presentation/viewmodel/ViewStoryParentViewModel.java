package com.dvk.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.dvk.datastore.api.OnGoalFetchedCallback;
import com.dvk.model.Goal;
import com.dvk.repository.Repository;

import java.util.List;

public class ViewStoryParentViewModel extends AndroidViewModel {
    Repository repository;
    public ViewStoryParentViewModel(@NonNull Application application, Repository repository) {
        super(application);
        this.repository = repository;
    }


    public void removeGoal(Goal goal){
        repository.deleteGoal(goal);
    }

    public void fetchAllGoals(FetchedGoal fetchedGoal){
        repository.fetchAllGoals(new OnGoalFetchedCallback() {
            @Override
            public void onGoalFetched(List<Goal> goals) {
                if(fetchedGoal!=null)
                fetchedGoal.goalsFetched(goals);
            }
        });
    }

    public interface FetchedGoal{
        void goalsFetched(List<Goal> goals);
    }
}
