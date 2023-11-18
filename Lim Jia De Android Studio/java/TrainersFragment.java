package com.example.testing;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.SQLDataException;
import java.util.ArrayList;

public class TrainersFragment extends Fragment implements  TrainersAchievementsInterface{

    private RecyclerView recyclerView;
    private ArrayList<User> trainers;
    private DatabaseManager dbManager;
    private User trainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trainers, container, false);
        trainer = new User();
        trainers = new ArrayList<>();
        dbManager = new DatabaseManager(requireActivity());
        try {
            dbManager.open();
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        trainers = dbManager.getAllTrainers();
        dbManager.close();

        recyclerView.setAdapter(new TrainerAdapter(requireActivity(), trainers, this::onItemClick));

        return view;
    }

    @Override
    public void onItemClick(int position) {
        User trainer = trainers.get(position);
        Intent i = new Intent(requireActivity(), TrainersAchievements.class);
        i.putExtra("trainer", trainer);
        startActivity(i);
    }
}