package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Login extends AppCompatActivity {

    private Button loginBtn, registerBtn, clearAllBtn;
    private EditText usernameTextField, passwordTextField;
    private Account account;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbManager = new DatabaseManager(this);

        try {
            dbManager.open();
        } catch (Exception e){
            e.printStackTrace();
        }

        usernameTextField = findViewById(R.id.usernameTextField);
        usernameTextField.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
        passwordTextField = findViewById(R.id.passwordTextField);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.goToRegisterBtn);
        clearAllBtn = findViewById(R.id.clearLoginBtn);

        account = new Account();
        setupButtonListeners();

        int input = dbManager.getFirstTableInput();
        if (input != 1){
            //Below will be dummy data for the first time
            //Creating account
            dbManager.createNewAccount("JOAH", "joah123", "ACTIVE", "16-NOV-2023", "TRAINER");
            dbManager.createNewAccount("JIADE", "jiade123", "ACTIVE", "16-NOV-2023", "TRAINER");
            dbManager.createNewAccount("SOOHONG", "soohong", "ACTIVE", "16-NOV-2023", "ATHLETE");

            //Creating profiles
            //Joah (Trainer)
            dbManager.createProfile(1, "Joah Saw Huojing", "Male",
                    "joahsawhj@gmail.com", "0105066721", 23, 58, 175,
                    calculateBMI(175, 58), "Gymnasium International Event: Top 1");

            //Jia De (Trainer)
            dbManager.createProfile(2, "Lim Jia De", "Male",
                    "limjiade@gmail.com", "01134355", 21, 55, 167,
                    calculateBMI(167, 55), "Gymnasium International Event: Top 1");

            //Soo Hong (Athlete)
            dbManager.createProfile(3, "Tan Soo Hong", "Female",
                    "soohong@gmail.com", "01234567", 21, 80, 180,
                    calculateBMI(180, 80), "Sleep: Top 0");

            dbManager.createClass(1, "Yoga", "12:00:00",
                    "13:00:00", 60, "11-11-2023", "A101", "YES");
            dbManager.createClass(1, "Gymnasium", "12:00:00",
                    "13:00:00", 60, "12-11-2023", "A102", "YES");
            dbManager.createClass(2, "Martial Arts", "12:00:00",
                    "13:00:00", 60, "13-11-2023", "A101", "YES");

            //Creating announcement
            dbManager.createAnnouncement("Class cancel due to sick", "Class Cancellation");
            dbManager.createAnnouncement("Class YOGA will relocate to A11", "Class Relocation");

            dbManager.insertFirstTable();
        }
    }

    private double calculateBMI(double height, double weight) {
        double tempHeight = height / 100;
        return weight / (tempHeight * tempHeight);
    }

    private void setupButtonListeners() {
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (usernameTextField.getText().toString().trim() == "" || passwordTextField.getText().toString() == "") {
                    Toast.makeText(getApplicationContext(), "All fields are mandatory!", Toast.LENGTH_SHORT).show();
                    return;
                }

                account.setUsername(usernameTextField.getText().toString().trim());
                account.setPassword(passwordTextField.getText().toString());

                if (!account.login(dbManager)){
                    Toast.makeText(getApplicationContext(), "Wrong username or password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                account.setAccountDBID(dbManager);
                account.setRoleFromDB(dbManager);
                dbManager.close();

                if (account.getRole().equals("TRAINER")){
                    Intent i = new Intent(Login.this, TrainerNavigationPage.class);
                    i.putExtra("account", account);
                    startActivity(i);
                } else if (account.getRole().equals("ATHLETE")){
                    Intent i = new Intent(Login.this, NavigationPage.class);
                    i.putExtra("account", account);
                    startActivity(i);
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });

        clearAllBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                usernameTextField.setText("");
                passwordTextField.setText("");
            }
        });
    }
}