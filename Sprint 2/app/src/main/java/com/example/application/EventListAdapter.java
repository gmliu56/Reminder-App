package com.example.application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.MyViewHolder> {
    Context context;
    ArrayList<Task> list;

    public EventListAdapter(Context context, ArrayList<Task> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Task task = list.get(position);
        holder.activity.setText(String.valueOf(task.getTask_name()));
        holder.tips.setText(String.valueOf(task.getTips()));
        holder.time.setText(String.valueOf(task.getTime()));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView activity,tips,time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            activity = itemView.findViewById(R.id.Show_activity);
            tips = itemView.findViewById(R.id.Show_Tips);
            time = itemView.findViewById(R.id.Show_time_);
        }
    }
}
