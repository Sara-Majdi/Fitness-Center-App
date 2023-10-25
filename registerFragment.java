package com.example.testing;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class registerFragment extends Fragment {

    private Button backBtn, registerBtn;
    private EditText usernameRegisterTextField, passwordRegisterTextField, passwordRegisterTextField2;
    private Spinner roleSpinner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        roleSpinner = (Spinner) view.findViewById(R.id.roleSpinner);
        ArrayAdapter <String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add("Athlete");
        adapter.add("Trainer");
        roleSpinner.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }

    public void registerBtnOnClick (View v){
        MainActivity mainActivity = (MainActivity)getActivity();
        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);
    }

    public void backBtnOnClick (View v){
        MainActivity mainActivity = (MainActivity)getActivity();
        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);
    }
}