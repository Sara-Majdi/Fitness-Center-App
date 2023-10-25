package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private Button loginBtn, registerBtn;
    private EditText usernameTextField, passwordTextField;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = findViewById(R.id.roleSpinner);

    }

    public void loginBtnOnClick(View v){
        account = new Account();
        usernameTextField = findViewById(R.id.usernameTextField);
        passwordTextField = findViewById(R.id.passwordTextField);

        account.setUsername(usernameTextField.getText().toString().trim());
        account.setPassword(passwordTextField.getText().toString());
    }

    public void goToRegisterBtnOnClick(View v){
        Fragment fragment = new registerFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.loginPage, fragment).commit();
    }
}