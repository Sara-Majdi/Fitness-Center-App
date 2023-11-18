package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TrainersAchievements extends AppCompatActivity {

    private TextView displayAchievements;
    private User trainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainers_achievements);

        displayAchievements = findViewById(R.id.displayAchievements);
        trainer = (User) getIntent().getSerializableExtra("trainer");

        displayAchievements.setText(trainer.getAchievements());
    }
}