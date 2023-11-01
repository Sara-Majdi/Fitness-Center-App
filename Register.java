package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Register extends AppCompatActivity {
    private Button backBtn, registerBtn, clearBtn;
    private EditText usernameRegisterTextField, passwordRegisterTextField, passwordRegisterTextField2;
    private Spinner roleSpinner;
    private Account account;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        roleSpinner = findViewById(R.id.roleSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter.add("ATHLETE");
        adapter.add("TRAINER");
        roleSpinner.setAdapter(adapter);

        //Assigning text fields
        usernameRegisterTextField = findViewById(R.id.usernameRegisterTextField);
        usernameRegisterTextField.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
        passwordRegisterTextField = findViewById(R.id.passwordRegisterTextField);
        passwordRegisterTextField2 = findViewById(R.id.passwordRegisterTextField2);

        //Assigning buttons
        registerBtn = findViewById(R.id.registerBtn);
        backBtn = findViewById(R.id.backBtn);
        clearBtn = findViewById(R.id.clearBtn);

        dbManager = new DatabaseManager(this);
        try {
            dbManager.open();
        } catch(Exception e){
            e.printStackTrace();
        }

        //Setting up buttons function
        setupButtonListeners();
    }

    private void setupButtonListeners() {
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameRegisterTextField.getText().toString().trim();
                String password1 = passwordRegisterTextField.getText().toString();
                String password2 = passwordRegisterTextField2.getText().toString();

                // Check if any of the required fields are empty
                if (username.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "All fields are mandatory!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the passwords match
                if (!password1.equals(password2)) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (dbManager.isUsernameTaken(username)){
                    Toast.makeText(getApplicationContext(), "Username has already taken!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    dbManager.createNewAccount(username, password1, "ACTIVE", dateFormat.format(currentDate), roleSpinner.getSelectedItem().toString());
                    Toast.makeText(getApplicationContext(), "Account successfully created!", Toast.LENGTH_SHORT).show();

                    dbManager.close();
                    Intent i = new Intent(Register.this, Login.class);
                    startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "An error occurred during registration.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> usernames = dbManager.fetchUsername();
                for (int i=0; i<usernames.size(); i++){
                    Log.i("DATABASE_TAG", "Username: " + usernames.get(i));
                }
                /*
                Cursor cursor = dbManager.fetchAccountTable();

                if (cursor.moveToFirst()){
                    do{
                        String username = cursor.getString(0);
                        Log.i("DATABASE_TAG", "Username: " + username);
                    } while (cursor.moveToNext());
                } */

                Intent i = new Intent(Register.this, Login.class);
                startActivity(i);
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                usernameRegisterTextField.setText("");
                passwordRegisterTextField2.setText("");
                passwordRegisterTextField.setText("");
            }
        });
    }
}