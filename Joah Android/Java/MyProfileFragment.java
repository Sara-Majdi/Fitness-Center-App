package com.example.testing;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLDataException;

public class MyProfileFragment extends Fragment {

    private TextView name, gender, contactNo, email, age, weight, height, bmi, result;
    private Account account;
    private Button updateDetailsBtn, viewMyAchievementsBtn;
    private DatabaseManager dbManager;
    private ImageView profilePicBTN;
    private static final int PICK_IMAGE_REQUEST = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        account = new Account();

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
        viewMyAchievementsBtn = view.findViewById(R.id.viewMyAchievementsBtn);
        profilePicBTN = view.findViewById(R.id.profilePicBTN);
        dbManager = new DatabaseManager(requireActivity());

        try {
            dbManager.open();
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }

        Bundle args = getArguments();
        if (args != null && args.containsKey("account")) {
            account = (Account) args.getSerializable("account");
        }

        if (account.getRole().equals("ATHLETE")){
            viewMyAchievementsBtn.setVisibility(view.GONE);
        } else if (!account.isHavingProfile()){
            viewMyAchievementsBtn.setVisibility(view.GONE);
        } else {
            viewMyAchievementsBtn.setVisibility(view.VISIBLE);
        }

        account.setProfile(dbManager);
        dbManager.close();

        if (account.isHavingProfile()){
            account.getUserDetails().calculateBMI();
            name.setText(account.getUserDetails().getName());
            gender.setText(account.getUserDetails().getGender());
            contactNo.setText(account.getUserDetails().getContactNo());
            email.setText(account.getUserDetails().getEmail());
            age.setText("Age: " + account.getUserDetails().getAge());
            weight.setText("Weight(kg): " + account.getUserDetails().getWeight());
            height.setText("Height(cm): " + account.getUserDetails().getHeight());
            bmi.setText(String.format("BMI: %.2f", account.getUserDetails().getBMI()));
            String imagePath = account.getUserDetails().getImagePath();
            if (imagePath != null && !imagePath.isEmpty()) {
                Uri imageUri = Uri.parse(imagePath);
                profilePicBTN.setImageURI(imageUri);
            }

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

        viewMyAchievementsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), TrainerViewAchievementsActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        });

        profilePicBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            try {
                ContentResolver contentResolver = requireActivity().getContentResolver();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri);
                profilePicBTN.setImageBitmap(bitmap);

                // Save the image path to the database
                saveImagePathToDatabase(selectedImageUri.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void saveImagePathToDatabase(String imagePath) {
        // Open the database
        try {
            dbManager.open();
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }

        // Update the image path in the database
        long result = dbManager.updateImagePath(dbManager.getProfileDBID(account.getAccountDBID()), imagePath);

        // Check the result of the update
        if (result != -1) {
            // Successfully updated
            Log.d("MyProfileFragment", "Image path updated successfully");
        } else {
            // Failed to update
            Log.e("MyProfileFragment", "Failed to update image path");
        }

        // Close the database
        dbManager.close();
    }
}