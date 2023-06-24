package com.example.jien;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView cardsBar;
    private Calendar calendar;
    private int currentHour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        init();
    }

    private void init(){
        cardsBar = findViewById(R.id.cardBar);

        List<Card> cards = new ArrayList<>();
        if (currentHour >= 7 && currentHour < 12){
            cards.add(new Card(true,R.drawable.brochure,"Morning Questions"));
        }else{
            cards.add(new Card(false,R.drawable.brochure,"Morning Questions"));
        }

        if (currentHour >= 12 && currentHour <= 14){
            cards.add(new Card(true,R.drawable.namecard,"Afternoon Questions"));
        }else{
            cards.add(new Card(false,R.drawable.namecard,"Afternoon Questions"));
        }

        if (currentHour >= 17 && currentHour <= 24){
            cards.add(new Card(true,R.drawable.poster,"Night Questions"));
        }else{
            cards.add(new Card(false,R.drawable.poster,"Night Questions"));
        }


        CardsAdapter.OnCardClickListener onCardClickListener = new CardsAdapter.OnCardClickListener() {
            @Override
            public void onCardClick(Card card) {
                if (!card.isAnswered()){
                    Toast.makeText(DashboardActivity.this, "Sorry, It's not the right time to answer this questionary", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(DashboardActivity.this, QuestionsActivity.class);
                if (card.getTitle() == "Night Questions"){
                    intent.putExtra("isNoon", true);
                }
                startActivity(intent);
            }
        };

        CardsAdapter adapter = new CardsAdapter(cards, this, onCardClickListener);
        cardsBar.setAdapter(adapter);
        cardsBar.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false));
        cardsBar.addItemDecoration(new CardsDecoration(10));

    }
}


