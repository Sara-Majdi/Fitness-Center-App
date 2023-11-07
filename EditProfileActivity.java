package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditProfileActivity extends AppCompatActivity {

    private EditText editTextName, editTextContactNo, editTextEmail;
    private Spinner genderSpinner;
    private Button nextBtn;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        account = (Account) getIntent().getSerializableExtra("account");

        Log.i("At Edit profile", "---------------------------------------");
        Log.i("Account Details", "Name: " + account.getUserDetails().getName());
        Log.i("Account Details", "Gender: " + account.getUserDetails().getGender());
        Log.i("Account Details", "Email: " + account.getUserDetails().getEmail());
        Log.i("Account Details", "Age: " + account.getUserDetails().getAge());
        Log.i("Account Details", "Weight: " + account.getUserDetails().getWeight());
        Log.i("Account Details", "Height: " + account.getUserDetails().getHeight());

        editTextName = findViewById(R.id.editTextName);
        editTextContactNo = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);
        nextBtn = findViewById(R.id.nextBtn);

        genderSpinner = findViewById(R.id.genderSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter.add("Male");
        adapter.add("Female");
        genderSpinner.setAdapter(adapter);
        setupButtonListeners();
    }

    private void setupButtonListeners() {
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextName.getText().toString().trim().equals("") ||
                        editTextContactNo.getText().toString().trim().equals("") ||
                        editTextEmail.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "All fields are mandatory!", Toast.LENGTH_SHORT).show();
                    return;
                }

                account.setUser(new User());

                account.getUserDetails().setName(editTextName.getText().toString().trim());
                account.getUserDetails().setContactNo(editTextContactNo.getText().toString().trim());
                account.getUserDetails().setEmail(editTextEmail.getText().toString().trim());
                account.getUserDetails().setGender(genderSpinner.getSelectedItem().toString().toUpperCase());

                Intent i = new Intent(EditProfileActivity.this, EditProfileInformation.class);
                i.putExtra("account", account);
                startActivity(i);
            }
        });
    }
}