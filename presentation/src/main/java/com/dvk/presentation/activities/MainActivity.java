package com.dvk.presentation.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dvk.presentation.R;
import com.dvk.presentation.Utility.Util;

public class MainActivity extends AppCompatActivity  {
    Button createNewStory,viewAllStories;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = this;
        createNewStory = findViewById(R.id.createNewStory);
        viewAllStories = findViewById(R.id.viewAllStories);
        createNewStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,CreateStoryActivity.class);
                startActivityForResult(intent, Util.MAIN_REQUEST_CODE);
            }
        });

        viewAllStories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewStoryParentActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Util.MAIN_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Toast.makeText(this,"successfully added",Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(this,"denied",Toast.LENGTH_SHORT).show();
        }
    }
}
