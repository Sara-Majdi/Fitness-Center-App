package com.example.testing;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MyProfileFragment extends Fragment {

    private TextView name, gender, contactNo, email, age, weight, height, bmi, result;
    private Account account;
    private Button updateDetailsBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        name = view.findViewById(R.id.name);
        gender = view.findViewById(R.id.gender);
        contactNo = view.findViewById(R.id.contactNo);
        email = view.findViewById(R.id.email);
        age = view.findViewById(R.id.age);
        weight = view.findViewById(R.id.weight);
        height = view.findViewById(R.id.height);
        bmi = view.findViewById(R.id.bmi);
        result = view.findViewById(R.id.result);
        updateDetailsBtn = view.findViewById(R.id.updateDetailsBtn);

        Bundle args = getArguments();
        if (args != null && args.containsKey("account")) {
            account = (Account) args.getSerializable("account");
        }

        if (account.isHavingProfile()){
            account.getUserDetails().setBMI();
            name.setText("Name: " + account.getUserDetails().getName());
            gender.setText("Gender: " + account.getUserDetails().getGender());
            contactNo.setText("Contact No.: " + account.getUserDetails().getContactNo());
            email.setText("Email: " + account.getUserDetails().getEmail());
            age.setText("Age: " + account.getUserDetails().getAge());
            weight.setText("Weight(kg): " + account.getUserDetails().getWeight());
            height.setText("Height(cm): " + account.getUserDetails().getHeight());
            bmi.setText(String.format("BMI: %.2f", account.getUserDetails().getBMI()));
            if (account.getUserDetails().getBMI() >= 18.5 && account.getUserDetails().getBMI() <= 24.9){
                result.setText("HEALTHY");
                result.setTextColor(Color.GREEN);
            } else if (account.getUserDetails().getBMI() < 18.5){
                result.setText("UNDERWEIGHT");
                result.setTextColor(Color.rgb(255, 165, 0));
            } else if (account.getUserDetails().getBMI() >= 25 && account.getUserDetails().getBMI() <= 29.9){
                result.setText("OVERWEIGHT");
                result.setTextColor(Color.rgb(255, 165, 0));
            } else if (account.getUserDetails().getBMI() > 30){
                result.setText("OBESE");
                result.setTextColor(Color.RED);
            } else {
                result.setText("Error");
                result.setTextColor(Color.RED);
            }
        }

        updateDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    public String getBMIResult(double bmi){
        String result = "";

        if (bmi >= 18.5 && bmi <= 24.9){
            result = "Healthy";
        } else if (bmi < 18.5){
            result = "Underweight";
        } else if (bmi >= 25 && bmi <= 29.9){
            result = "Overweight";
        } else if (bmi > 30){
            result = "Obese";
        } else {
            result = "Error";
        }
        return result;
    }
}