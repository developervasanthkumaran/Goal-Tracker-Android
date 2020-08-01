package com.dvk.presentation.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dvk.model.Goal;
import com.dvk.presentation.R;
import com.dvk.presentation.activities.CreateStoryActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public  class ViewStoryParentAdapter extends RecyclerView.Adapter<ViewStoryParentAdapter.ViewHolder>  {
    private Context context;
    private LayoutInflater inflater;
    private List<Goal> goalList;
    public ViewStoryParentAdapter(Context context){
        this.context=context;
        goalList = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_viewgoal,parent,false);
        return new ViewStoryParentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.goalSetter.setText(goalList.get(holder.getAdapterPosition()).getStatement());
        holder.goalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CreateStoryActivity.class);
                intent.putExtra("Goal",(Serializable)goalList.get(holder.getAdapterPosition()));
                (context).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        if(goalList!=null){
            Log.i("size",String.valueOf(goalList.size()));
        return goalList.size();}
        else return 0;
    }

   static class ViewHolder extends RecyclerView.ViewHolder {
        TextView goalSetter;
        CardView goalCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            goalSetter=itemView.findViewById(R.id.goals);
            goalCard  =itemView.findViewById(R.id.goalCard);
        }
    }

    public List<Goal> getGoalList() {
        return goalList;
    }

    public void setGoalList(List<Goal> goalList) {
        this.goalList = goalList;
    }
}
