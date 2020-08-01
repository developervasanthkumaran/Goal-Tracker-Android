package com.dvk.datastore.api;

import com.dvk.model.Goal;

import java.util.List;

public interface OnGoalFetchedCallback {
    void onGoalFetched(List<Goal> goals);
}
