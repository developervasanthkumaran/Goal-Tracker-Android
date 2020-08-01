package com.dvk.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.dvk.presentation.R;
import com.dvk.presentation.viewmodel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteActivity extends AppCompatActivity {
    EditText noteArea;
    NoteViewModel viewModel;
    FloatingActionButton noteSaver;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
         intent = getIntent();
        String note = (String) intent.getStringExtra("Note");
        noteArea = findViewById(R.id.noteArea);
        noteSaver = findViewById(R.id.saveNoteButton);
        viewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        if(note!=null && !note.isEmpty()){
            viewModel.setNotes(note);
            noteArea.setText(note);
        }
        noteArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                        String str = s.toString();
                        if(!str.isEmpty()) {
                            viewModel.setNotes(str);
                        }
            }
        });

        noteSaver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.putExtra("Note",viewModel.getNotes());
                Integer i = intent.getIntExtra("Position",0);
                intent1.putExtra("Position",i);
                setResult(RESULT_OK,intent1);
                finish();
            }
        });
    }

}