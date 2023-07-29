package com.example.jien;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        FloatingActionButton waterBtn =  findViewById(R.id.waterBtn);
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
        waterBtn.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this,WaterReminderActivity.class);
            startActivity(intent);
        });
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.settings) {
                Intent intent = new Intent(DashboardActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });

        Calendar calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        init();
    }

    private void init(){

        new TopBarHelper(this);

        RecyclerView cardsBar = findViewById(R.id.cardBar);

        List<Card> cards = new ArrayList<>();
        if (currentHour >= 7 && currentHour < 12){
            cards.add(new Card(true,R.drawable.morningq,getString(R.string.morning_questions)));
        }else{
            cards.add(new Card(false,R.drawable.morningq,getString(R.string.morning_questions)));
        }

        if (currentHour >= 12 && currentHour <= 14){
            cards.add(new Card(true,R.drawable.afternoonq,getString(R.string.afternoon_questions)));
        }else{
            cards.add(new Card(false,R.drawable.afternoonq,getString(R.string.afternoon_questions)));
        }


        if (currentHour >= 17 && currentHour <= 19){

            cards.add(new Card(true,R.drawable.nightq,getString(R.string.night_questions)));
        }else{
            cards.add(new Card(false,R.drawable.nightq,getString(R.string.night_questions)));
        }


        CardsAdapter.OnCardClickListener onCardClickListener = card -> {
            if (!card.isAnswered()){
                Toast.makeText(DashboardActivity.this, getString(R.string.sorry_it_s_not_the_right_time_to_answer_this_questioner), Toast.LENGTH_LONG).show();
                return;
            }
            Intent intent = new Intent(DashboardActivity.this, QuestionsActivity.class);
            if (card.getTitle().equals(getString(R.string.night_questions))){
                intent.putExtra(getString(R.string.isnoon), true);
            }
            startActivity(intent);
        };

        CardsAdapter adapter = new CardsAdapter(cards, this, onCardClickListener);
        cardsBar.setAdapter(adapter);
        cardsBar.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false));
        cardsBar.addItemDecoration(new CardsDecoration(10));

    }
}


