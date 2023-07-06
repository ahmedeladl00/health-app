package com.example.jien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {
    private static List<PhysicalActivities> activities;
    private Context ctx;
    //  private static OnActivityClickListener onActivityClickListener;


    public ActivityAdapter(List<PhysicalActivities> activities, Context ctx){
        this.activities=activities;
        this.ctx = ctx;
        // this.onActivityClickListener = onActivityClickListener;
    }

    @NonNull
    @Override

    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.itemactivity,parent,false);
        ActivityViewHolder viewHolder=new ActivityViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        holder.activityName.setText(activities.get(position).getActivity());
        holder.timeFrom.setText(activities.get(position).getTimeFrom());
        holder.timeTo.setText(activities.get(position).getTimeTo());
        holder.day.setText(activities.get(position).getDay().toString());
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }
    public interface OnActivityClickListener{
        void onActivityClick(PhysicalActivities activity);
    }

    public static class ActivityViewHolder extends RecyclerView.ViewHolder {
        private TextView activityName;
        private TextView timeFrom;
        private TextView timeTo;
        private TextView day;

        public ActivityViewHolder(View view) {
            super(view);
            activityName =view.findViewById(R.id.activityitem);
            timeFrom=view.findViewById(R.id.timefroite);
            timeTo=view.findViewById(R.id.timetoite);
            day=view.findViewById(R.id.dayite);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position =getAdapterPosition();
                    if(position !=RecyclerView.NO_POSITION){
                        PhysicalActivities activity=activities.get(position);
                        //onActivityClickListener.onActivityClick(activity);
                    }
                }
            });

        }
    }
}
