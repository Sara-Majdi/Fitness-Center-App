package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLDataException;

public class TrainerUpdateAchievements extends AppCompatActivity {

    Account account;
    DatabaseManager dbManager;
    Button confirmBtn;
    EditText editAchievementsSpace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_update_achievements);
        dbManager = new DatabaseManager(this);
        confirmBtn = findViewById(R.id.updateBtn);
        editAchievementsSpace = findViewById(R.id.editAchievementsSpace);

        account = (Account) getIntent().getSerializableExtra("account");

        try {
            dbManager.open();
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account.getUserDetails().setAchievements(editAchievementsSpace.getText().toString().trim());
                dbManager.setAchievements(account.getAccountDBID(), account.getUserDetails().getAchievements());
                Toast.makeText(getApplicationContext(), "Achievements updated successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TrainerUpdateAchievements.this, TrainerNavigationPage.class);
                intent.putExtra("account", account);
                intent.putExtra("pagekey", 1);
                startActivity(intent);
            }
        });


    }
}