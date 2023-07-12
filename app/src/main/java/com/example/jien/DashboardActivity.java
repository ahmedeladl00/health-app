package com.example.jien;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private int currentHour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        LinearLayout interList = findViewById(R.id.interList);
        LinearLayout interRecord = findViewById(R.id.interRecord);
        LinearLayout lastInter = findViewById(R.id.lastInter);
        LinearLayout activityList = findViewById(R.id.activityList);
        LinearLayout activityRecord = findViewById(R.id.activityRecord);
        LinearLayout phyDevelopment = findViewById(R.id.phyDevelopment);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);

        interList.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, InterventionActivity.class);
            startActivity(intent);
        });
        interRecord.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, InterventionRecordActivity.class);
            startActivity(intent);
        });
        lastInter.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, InterventionListActivity.class);
            startActivity(intent);
        });
        activityList.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, PhysicalActivity.class);
            startActivity(intent);
        });
        activityRecord.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ActiPhysList.class);
            startActivity(intent);
        });
        phyDevelopment.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, PhyDevelopmentActivity.class);
            startActivity(intent);
        });
        Calendar calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        init();
    }

    private void init(){
        RecyclerView cardsBar = findViewById(R.id.cardBar);

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


        if (currentHour >= 17 && currentHour <= 19){

            cards.add(new Card(true,R.drawable.poster,"Night Questions"));
        }else{
            cards.add(new Card(false,R.drawable.poster,"Night Questions"));
        }


        CardsAdapter.OnCardClickListener onCardClickListener = card -> {
            if (!card.isAnswered()){
                Toast.makeText(DashboardActivity.this, "Sorry, It's not the right time to answer this questioner", Toast.LENGTH_LONG).show();
                return;
            }
            Intent intent = new Intent(DashboardActivity.this, QuestionsActivity.class);
            if (card.getTitle().equals("Night Questions")){
                intent.putExtra("isNoon", true);
            }
            startActivity(intent);
        };

        CardsAdapter adapter = new CardsAdapter(cards, this, onCardClickListener);
        cardsBar.setAdapter(adapter);
        cardsBar.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false));
        cardsBar.addItemDecoration(new CardsDecoration(10));

    }
}


