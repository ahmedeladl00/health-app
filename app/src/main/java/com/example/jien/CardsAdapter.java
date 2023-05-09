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

    private List<Card> cards;
    private Context context;

    public CardsAdapter(List<Card> cards, Context context){
        this.cards = cards;
        this.context = context;
    }


    @NonNull
    @Override
    public CardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item,parent,false);
        CardsViewHolder viewHolder = new CardsViewHolder(view);
        return viewHolder;
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

    public static class CardsViewHolder extends RecyclerView.ViewHolder{
        private CardView cardOutline;
        private ImageView cardImage;
        private TextView cardName;
        public CardsViewHolder(@NonNull View itemView){
            super(itemView);
            cardOutline = itemView.findViewById(R.id.cardOutline);
            cardImage = itemView.findViewById(R.id.cardImage);
            cardName = itemView.findViewById(R.id.cardName);
        }
    }
}
