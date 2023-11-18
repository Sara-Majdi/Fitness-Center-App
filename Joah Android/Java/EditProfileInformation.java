package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

public class EditProfileInformation extends AppCompatActivity {

    private EditText editTextAge, editTextHeight, editTextWeight;
    private Button cfmButton;
    private Account account;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_information);

        account = (Account) getIntent().getSerializableExtra("account");
        dbManager = new DatabaseManager(this);
        try {
            dbManager.open();
        } catch (Exception e){
            e.printStackTrace();
        }

        editTextAge = findViewById(R.id.editTextAge);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        cfmButton = findViewById(R.id.cfmBtn);
        setupButtonListeners();
    }

    private void setupButtonListeners() {
        cfmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int age = Integer.parseInt(editTextAge.getText().toString().trim());
                double weight = Double.parseDouble(editTextWeight.getText().toString().trim());
                double height = Double.parseDouble(editTextHeight.getText().toString().trim());

                if (age <= 0 || weight <= 0 || height <= 0) {
                    Toast.makeText(getApplicationContext(), "Please enter valid values for age, weight, and height.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (editTextAge.getText().toString().trim().equals("") || editTextHeight.getText().toString().trim().equals("") ||
                editTextWeight.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "All fields are mandatory!", Toast.LENGTH_SHORT).show();
                    return;
                }

                account.getUserDetails().setAge(age);
                account.getUserDetails().setWeight(weight);
                account.getUserDetails().setHeight(height);

                if (account.isHavingProfile()){
                    //Update profile
                    dbManager.updateProfile(account.getAccountDBID(),
                            account.getUserDetails().getName(),
                            account.getUserDetails().getGender(),
                            account.getUserDetails().getEmail(),
                            account.getUserDetails().getContactNo(),
                            account.getUserDetails().getAge(),
                            account.getUserDetails().getWeight(),
                            account.getUserDetails().getHeight(),
                            account.getUserDetails().getBMI(),
                            account.getUserDetails().getAchievements());
                } else {
                    //Create profile
                    dbManager.createProfile(
                            account.getAccountDBID(),
                            account.getUserDetails().getName(),
                            account.getUserDetails().getGender(),
                            account.getUserDetails().getEmail(),
                            account.getUserDetails().getContactNo(),
                            account.getUserDetails().getAge(),
                            account.getUserDetails().getWeight(),
                            account.getUserDetails().getHeight(),
                            account.getUserDetails().getBMI(),
                            account.getUserDetails().getAchievements());
                    dbManager.updateAccountHasProfile(account.getAccountDBID());
                }
                dbManager.close();

                if (account.getRole().equals("ATHLETE")){
                    Intent i = new Intent(EditProfileInformation.this, NavigationPage.class);
                    i.putExtra("account", account);
                    i.putExtra("pagekey", 1);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), "Profile details updated successfully!", Toast.LENGTH_SHORT).show();
                } else if (account.getRole().equals("TRAINER")){
                    Intent i = new Intent(EditProfileInformation.this, TrainerUpdateAchievements.class);
                    i.putExtra("account", account);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), "Profile details updated successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}