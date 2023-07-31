/*
 * MIT License
 *
 * Copyright (c) 2023 RUB-SE-LAB
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.example.jien;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InterActAdapter extends RecyclerView.Adapter<InterActAdapter.ViewHolder> {
    private final List<IABase> interActItems;
    private final OnItemClickListener clickListener;

    public InterActAdapter(List<IABase> interActItems, OnItemClickListener clickListener) {
        this.interActItems = interActItems;
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
        IABase intervention = interActItems.get(position);

        holder.interActNameTextView.setText(intervention.getName());
        holder.dateTextView.setText(intervention.getDay().toString());
        holder.timeFromTextView.setText(intervention.getTimeFrom());
        holder.timeToTextView.setText(intervention.getTimeTo());
    }

    @Override
    public int getItemCount() {
        return interActItems.size();
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
