package com.dvk.presentation.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dvk.model.Event;
import com.dvk.model.Goal;
import com.dvk.presentation.AppInitializer.Initializer;
import com.dvk.presentation.Factory.ViewModelFactory;
import com.dvk.presentation.R;
import com.dvk.presentation.Utility.Util;
import com.dvk.presentation.adapters.CreateStoryAdapter;
import com.dvk.presentation.viewmodel.CreateStoryViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;


public class CreateStoryActivity extends AppCompatActivity  {

    EditText goalStatement;
    FloatingActionButton save,addEvent,refresh;
    RecyclerView eventRecyclerView;
    CreateStoryAdapter createStoryAdapter;
    RecyclerView.LayoutManager layoutManager;
    EditText noteArea;
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         context = this;
        setContentView(R.layout.activity_createstory);
        goalStatement = findViewById(R.id.addGoalStatement);
        save = findViewById(R.id.sessionSaveButton);
        refresh = findViewById(R.id.refresh);
        addEvent = findViewById(R.id.eventAdderButton);
        eventRecyclerView = findViewById(R.id.rEventList);
        layoutManager = new LinearLayoutManager(this);
        createStoryAdapter = new CreateStoryAdapter(this);
        eventRecyclerView.setLayoutManager(layoutManager);
        eventRecyclerView.setAdapter(createStoryAdapter);
        Initializer initializer = (Initializer) getApplication();
        ViewModelFactory factory = new ViewModelFactory(getApplication(),initializer.getRepository());
        CreateStoryViewModel viewModel = new  ViewModelProvider(this,factory).get(CreateStoryViewModel.class);
        Intent intent = getIntent();
        Goal goal = (Goal)intent.getSerializableExtra("Goal");
        if(goal!=null){
            goalStatement.setText(goal.getStatement());
            viewModel.setGoal(goal.getStatement());
            viewModel.setEvents(goal.getEventList());
            createStoryAdapter.setEventList(goal.getEventList());
            createStoryAdapter.notifyDataSetChanged();
        }
        goalStatement.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty())
                viewModel.setGoal(s.toString());
                else showWarning();
            }
        });
        eventRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
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
                Event event = createStoryAdapter.getEventList().get(viewHolder.getAdapterPosition());
                viewModel.removeEvent(event);
                cancelAlarmForEvent(event);
                createStoryAdapter.getEventList().remove(event);
                createStoryAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                createStoryAdapter.notifyDataSetChanged();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(eventRecyclerView);

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewModel.getGoal()!=null&&!viewModel.getGoal().getStatement().isEmpty()){
                    Intent intent = new Intent(context, EventActivity.class);
                    startActivityForResult(intent, Util.CREATE_STORY_REQUEST_CODE);
                }
                else showWarning();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewModel.getGoal()!=null&&!viewModel.getGoal().getStatement().isEmpty()){
                    viewModel.setEvents(createStoryAdapter.getEventList());
                    viewModel.insertStoryToRepository();
                    setResult(Util.MAIN_REQUEST_CODE);
                    setResult(RESULT_OK);
                    finish();
                }
                else showWarning();
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createStoryAdapter.notifyDataSetChanged();
            }
        });
    }

    public void showWarning(){
        Toast.makeText(this,"you must enter title",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== Util.CREATE_STORY_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Event event = (Event) data.getSerializableExtra("Event");
                if(event!=null){
                if(event.getDescription().isEmpty())Toast.makeText(this,"denied",Toast.LENGTH_SHORT).show();
                else {
                    createStoryAdapter.getEventList().add(event);
                    createStoryAdapter.notifyDataSetChanged();
                }
                }
            }
        }
        if(requestCode== Util.CREATE_NOTE_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Log.i("notes "," updated");
                Log.i("event index ",""+createStoryAdapter.getCurrentPosition());
                String note = data.getStringExtra("Note") ;
                int pos = data.getIntExtra("Position",0);
                createStoryAdapter.getEventList().get(pos).setNotes(note);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        createStoryAdapter.notifyDataSetChanged();
    }

    public void cancelAlarmForEvent(Event event){
        Calendar newTime = event.getTime();
        Intent intent = new Intent(this,Alarm.class);
        intent.putExtra("Calendar",newTime);
        intent.putExtra("Event",event.getDescription());
        intent.putExtra("isToStartAlarm",false);
        startActivity(intent);
    }


}
