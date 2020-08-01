package com.dvk.presentation.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.dvk.presentation.R;
import com.dvk.presentation.Utility.Util;
import com.dvk.presentation.viewmodel.EventViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
public class EventActivity extends AppCompatActivity implements Serializable {
    EventViewModel viewModel;
    EditText eventDes;
    ImageView notes;
    Button setDate,setTime;
    FloatingActionButton back;
    final Calendar newCalendar = Calendar.getInstance();
    Calendar newDate;
    Calendar newTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Context context = this;
        eventDes = findViewById(R.id.addEventDescription);
        notes = findViewById(R.id.noteIcon);
        setDate = findViewById(R.id.setDate);
        setTime = findViewById(R.id.setTime);
        viewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        back = findViewById(R.id.back);
        eventDes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            viewModel.setDescription(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setDescription(s.toString());
            }
        });
        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String des = viewModel.getEvent().getDescription();
                if(viewModel.getEvent()!=null&&des!=null&&!des.isEmpty()) {
                    new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                             newDate = Calendar.getInstance();
                            newDate.set(year, monthOfYear, dayOfMonth);
                            Log.i("date ",""+year+" "+monthOfYear+" "+dayOfMonth);
                            if(newDate.after(newCalendar) || newDate.equals(newCalendar)){
                            setDate.setText(Util.dateFormatter(newDate));
                            viewModel.setDate(newDate);}
                            else Toast.makeText(context,"you entered elapsed date! try again",Toast.LENGTH_SHORT).show();

                        }

                    }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
                else showWarning();
        }});

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String des = viewModel.getEvent().getDescription();
                if(viewModel.getEvent()!=null&&des!=null&&!des.isEmpty()){
                    new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                 newTime = Calendar.getInstance();
                                newTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                newTime.set(Calendar.MINUTE,minute);
                            Log.i("time ",""+hourOfDay+" "+minute);
                            Log.i("am_pm",String.valueOf(newTime.get(Calendar.AM_PM)));
                            try {
                                if(newDate.get(Calendar.DAY_OF_MONTH)==newCalendar.get(Calendar.DAY_OF_MONTH)&&newTime.after(newCalendar)) {
                                    Log.i("before time ",""+newTime.before(newCalendar));
                                    Log.i("equal date ",""+newDate.equals(newCalendar));
                                    setTime.setText(Util.timeFormatter(newTime));
                                    viewModel.setTime(newTime);
                                    startAlarm();
                                }
                                else if(newDate.get(Calendar.DAY_OF_MONTH)>newCalendar.get(Calendar.DAY_OF_MONTH)){
                                    Log.i("current date",""+newCalendar.getTime());
                                    Log.i("after date ",""+newDate.getTime());
                                    setTime.setText(Util.timeFormatter(newTime));
                                    viewModel.setTime(newTime);
                                    startAlarm();
                                }
                                else Toast.makeText(context,"you entered elapsed time! try again",Toast.LENGTH_SHORT).show();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            }

                    }, newCalendar.get(Calendar.HOUR),
                            newCalendar.get(Calendar.MINUTE),
                            false).show();
            }else showWarning();
        }});
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(context, CreateStoryActivity.class);
                    intent.putExtra("Event",(Serializable) viewModel.getEvent());
                    setResult(RESULT_OK,intent);
                    finish();
            }
        });
    }
    public void showWarning(){
        Toast.makeText(this,"you must enter event description",Toast.LENGTH_SHORT).show();
    }
    public void startAlarm(){
        Intent intent = new Intent(this,Alarm.class);
        intent.putExtra("Calendar",newTime);
        intent.putExtra("Event",viewModel.getEvent().getDescription());
        intent.putExtra("isToStartAlarm",true);
        startActivity(intent);
    }
}