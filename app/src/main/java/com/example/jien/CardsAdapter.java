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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardsViewHolder> {

    private static List<Card> cards;
    private final Context context;
    private static OnCardClickListener onCardClickListener;

    public CardsAdapter(List<Card> cards, Context context, OnCardClickListener onCardClickListener){
        CardsAdapter.cards = cards;
        this.context = context;
        CardsAdapter.onCardClickListener = onCardClickListener;
    }


    @NonNull
    @Override
    public CardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item,parent,false);
        return new CardsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardsViewHolder holder, int position) {
        holder.cardImage.setImageResource(cards.get(position).getImage());
        holder.cardName.setText(cards.get(position).getTitle());
        if (cards.get(position).isAnswered()) {
            holder.cardOutline.setCardBackgroundColor(ContextCompat.getColor(context, R.color.success));
        } else {
            holder.cardOutline.setCardBackgroundColor(ContextCompat.getColor(context, R.color.danger));
        }
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public interface OnCardClickListener {
        void onCardClick(Card card);
    }

    public static class CardsViewHolder extends RecyclerView.ViewHolder{
        private final CardView cardOutline;
        private final ImageView cardImage;
        private final TextView cardName;
        public CardsViewHolder(@NonNull View itemView){
            super(itemView);
            cardOutline = itemView.findViewById(R.id.cardOutline);
            cardImage = itemView.findViewById(R.id.cardImage);
            cardName = itemView.findViewById(R.id.cardName);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Card card = cards.get(position);
                    onCardClickListener.onCardClick(card);
                }
            });
        }
    }
}
