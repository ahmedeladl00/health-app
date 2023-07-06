package com.example.jien;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InterventionAdapter extends RecyclerView.Adapter<InterventionAdapter.ViewHolder> {
    private final List<Intervention> interventions;
    private final OnItemClickListener clickListener;

    public InterventionAdapter(List<Intervention> interventions,OnItemClickListener clickListener) {
        this.interventions = interventions;
        this.clickListener = clickListener;
    }

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.inter_act_item,parent,false);
        return new ViewHolder(view, this.clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Intervention intervention = interventions.get(position);

        holder.interActNameTextView.setText(intervention.getName());
        holder.dateTextView.setText(intervention.getDay().toString());
        holder.timeFromTextView.setText(intervention.getTimeFrom());
        holder.timeToTextView.setText(intervention.getTimeTo());
    }

    @Override
    public int getItemCount() {
        return interventions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView interActNameTextView;
        TextView dateTextView;
        TextView timeFromTextView;
        TextView timeToTextView;
        ImageView deleteIcon;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
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
