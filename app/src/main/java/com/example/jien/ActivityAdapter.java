package com.example.jien;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {
    private static List<IABase> activities;
    private final OnItemClickListener clickListener;


    public ActivityAdapter(List<IABase> activities, OnItemClickListener clickListener){
        this.activities=activities;
        this.clickListener = clickListener;
    }

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    @NonNull
    @Override

    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.inter_act_item,parent,false);
        ActivityViewHolder viewHolder=new ActivityViewHolder(view,this.clickListener);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        IABase activity = activities.get(position);

        holder.interActNameTextView.setText(activity.getName());
        holder.dateTextView.setText(activity.getDay().toString());
        holder.timeFromTextView.setText(activity.getTimeFrom());
        holder.timeToTextView.setText(activity.getTimeTo());
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }
    public interface OnActivityClickListener{
        void onActivityClick(PhysicalActivities activity);
    }

    public static class ActivityViewHolder extends RecyclerView.ViewHolder {
        TextView interActNameTextView;
        TextView dateTextView;
        TextView timeFromTextView;
        TextView timeToTextView;
        ImageView deleteIcon;

        public ActivityViewHolder(@NonNull View itemView, final ActivityAdapter.OnItemClickListener listener) {
            super(itemView);

            interActNameTextView = itemView.findViewById(R.id.interActName);
            dateTextView = itemView.findViewById(R.id.txtDate);
            timeFromTextView = itemView.findViewById(R.id.txtTimeFrom);
            timeToTextView = itemView.findViewById(R.id.txtTimeTo);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);

            deleteIcon.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(position);
                    }
                }
            });

        }
    }
}
