package com.dvk.presentation.Factory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.dvk.presentation.viewmodel.CreateStoryViewModel;
import com.dvk.presentation.viewmodel.ViewStoryParentViewModel;
import com.dvk.repository.Repository;

public class ViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    private final Application application;
    @NonNull
    private final Repository repository;

    public ViewModelFactory(@NonNull Application application, @NonNull Repository repository) {
        this.application = application;
        this.repository=repository;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == CreateStoryViewModel.class) {
            return (T) new CreateStoryViewModel(application ,repository);
        }
        else if (modelClass == ViewStoryParentViewModel.class) {
            return (T) new ViewStoryParentViewModel(application ,repository);
        }
        return null;
    }
}
