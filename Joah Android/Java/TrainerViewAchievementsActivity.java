package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.SQLDataException;

public class TrainerViewAchievementsActivity extends AppCompatActivity {

    private Account account;
    private DatabaseManager dbManager;
    private Button updateBtn;
    private TextView displayAchievementsTextView, backViewAchievementsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_achievements);
        account = new Account();
        dbManager = new DatabaseManager(this);
        updateBtn = findViewById(R.id.updateBtn);
        displayAchievementsTextView = findViewById(R.id.displayAchievementsTextView);
        backViewAchievementsBtn = findViewById(R.id.backViewAchievementsBtn);

        try {
            dbManager.open();
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }
        account = (Account) getIntent().getSerializableExtra("account");


        if (dbManager.getAchievements(account.getAccountDBID()).trim().equals("")){
            displayAchievementsTextView.setText("Please update your achievements!");
        } else {
            displayAchievementsTextView.setText(dbManager.getAchievements(account.getAccountDBID()));
        }

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainerViewAchievementsActivity.this, TrainerUpdateAchievements.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        });

        backViewAchievementsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainerViewAchievementsActivity.this, TrainerNavigationPage.class);
                intent.putExtra("account", account);
                intent.putExtra("pagekey", 1);
                startActivity(intent);
            }
        });

    }
}