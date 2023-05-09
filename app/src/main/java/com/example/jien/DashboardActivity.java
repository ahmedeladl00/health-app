package com.example.jien;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView cardsBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        init();
    }

    private void init(){
        cardsBar = findViewById(R.id.cardBar);

        List<Card> cards = new ArrayList<>();
        cards.add(new Card(true,R.drawable.brochure,"Morning Questions"));
        cards.add(new Card(false,R.drawable.namecard,"Afternoon Questions"));
        cards.add(new Card(false,R.drawable.poster,"Night Questions"));

        CardsAdapter adapter = new CardsAdapter(cards, this);
        cardsBar.setAdapter(adapter);
        cardsBar.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false));
        cardsBar.addItemDecoration(new CardsDecoration(10));

    }
}


