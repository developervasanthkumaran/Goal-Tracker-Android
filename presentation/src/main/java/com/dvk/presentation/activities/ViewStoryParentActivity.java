package com.dvk.presentation.activities;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dvk.model.Goal;
import com.dvk.presentation.AppInitializer.Initializer;
import com.dvk.presentation.Factory.ViewModelFactory;
import com.dvk.presentation.R;
import com.dvk.presentation.adapters.ViewStoryParentAdapter;
import com.dvk.presentation.viewmodel.ViewStoryParentViewModel;

import java.util.List;

public class ViewStoryParentActivity extends AppCompatActivity {
RecyclerView rShowContents;
ViewStoryParentAdapter adapter;
RecyclerView.LayoutManager layoutManager;
ViewStoryParentViewModel viewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewgoal);
        rShowContents = findViewById(R.id.rShowContent);
        adapter = new ViewStoryParentAdapter(this);
        layoutManager = new LinearLayoutManager(this);
        rShowContents.setLayoutManager(layoutManager);
        rShowContents.setAdapter(adapter);
        Initializer initializer = (Initializer) getApplication();
        ViewModelFactory factory = new ViewModelFactory(getApplication(),initializer.getRepository());
         viewModel = new ViewModelProvider(this,factory).get(ViewStoryParentViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.fetchAllGoals(new ViewStoryParentViewModel.FetchedGoal() {
            @Override
            public void goalsFetched(List<Goal> goals) {
                adapter.setGoalList(goals);
                adapter.notifyDataSetChanged();
                ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {
                    @Override
                    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                        int swipeFlags = ItemTouchHelper.RIGHT;
                        return makeMovementFlags(dragFlags, swipeFlags);
                    }

                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        Goal goal = adapter.getGoalList().get(viewHolder.getAdapterPosition());
                        viewModel.removeGoal(goal);
                        adapter.getGoalList().remove(goal);
                        adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                        adapter.notifyDataSetChanged();
                    }
                };
                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
                itemTouchHelper.attachToRecyclerView(rShowContents);
            }
        });
    }
}
