package com.dvk.presentation.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dvk.model.Event;
import com.dvk.presentation.R;
import com.dvk.presentation.Utility.Util;
import com.dvk.presentation.activities.NoteActivity;
import com.dvk.presentation.callbacks.NoteAreaFilledCallback;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class CreateStoryAdapter extends RecyclerView.Adapter<CreateStoryAdapter.ViewHolder>  {
    private Context context;
    private LayoutInflater inflater;
    private List<Event> eventList;
    private NoteAreaFilledCallback onNoteAreaFilledCallback;
    private int currentPosition;
    public CreateStoryAdapter(Context context){
        this.context=context;
        this.eventList=new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_createstory,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        setCurrentPosition(holder.getAdapterPosition());
        holder.description.setText(eventList.get(holder.getAdapterPosition()).getDescription());
        holder.scheduledDate.setText(Util.dateFormatter(eventList.get(holder.getAdapterPosition()).getDate()));
        try {
            holder.scheduledTime.setText(Util.timeFormatter(eventList.get(holder.getAdapterPosition()).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.remainingTime.setText(Util.getRemainingTime(eventList.get(holder.getAdapterPosition()).getDate(),
                eventList.get(holder.getAdapterPosition()).getTime()));
        holder.noteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NoteActivity.class);
                intent.putExtra("Note",eventList.get(holder.getAdapterPosition()).getNotes());
                intent.putExtra("Position",holder.getAdapterPosition());
                ((Activity) context).startActivityForResult(intent,Util.CREATE_NOTE_REQUEST_CODE);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.i("size",String.valueOf(eventList.size()));
        if(eventList!=null)
        return eventList.size();
        else return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView description, scheduledDate, scheduledTime,remainingTime;
        CardView eventCard;
        ImageView noteIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.EventDes);
            scheduledDate = itemView.findViewById(R.id.ScheduledDate);
            scheduledTime = itemView.findViewById(R.id.ScheduledTime);
            remainingTime = itemView.findViewById(R.id.timeRemaining);
            eventCard = itemView.findViewById(R.id.EventCard);
            noteIcon = itemView.findViewById(R.id.noteIcon);
        }
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }
}
