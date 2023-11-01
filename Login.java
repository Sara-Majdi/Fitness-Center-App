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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

        setupButtonListeners();
    }

    private void setupButtonListeners() {
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (usernameTextField.getText().toString().trim() == "" || passwordTextField.getText().toString() == "") {
                    Toast.makeText(getApplicationContext(), "All fields are mandatory!", Toast.LENGTH_SHORT).show();
                    return;
                }
                account = new Account();

                account.setUsername(usernameTextField.getText().toString().trim());
                account.setPassword(passwordTextField.getText().toString());

                if (account.login(dbManager)){
                    dbManager.close();
                    Intent i = new Intent(Login.this, HomePage.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong username or password!", Toast.LENGTH_SHORT).show();
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