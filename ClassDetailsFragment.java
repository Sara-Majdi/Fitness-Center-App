package com.example.testing;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ClassDetailsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<MyClass> classes;
    private DatabaseManager dbManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_class_details, container, false);
        dbManager = new DatabaseManager(requireActivity());

        try {
            dbManager.open();
        } catch (Exception e){
            e.printStackTrace();
        }


        String startTime = "12:00:00", endTime = "13:00:00", classDate = "12-12-2023";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm:ss");

        Date startTimeDate, endTimeDate, classDateDate;
        try {
            startTimeDate = timeFormat.parse(startTime);
            endTimeDate = timeFormat.parse(endTime);
            classDateDate = dateFormat.parse(classDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        classes = dbManager.getAvailableClasses();
        dbManager.close();

        /*
        classes.add(new MyClass(new User("Navi", "012234334", "email", "Male", 23, 175, 59, 20.4),
                0, startTimeDate, endTimeDate, 60, classDateDate, "A11", "Yoga"));

        classes.add(new MyClass(new User("Savi", "0123343435", "email", "Male", 45, 180, 45, 23.4),
                 0, startTimeDate, endTimeDate, 60, classDateDate, "A45", "Gymnasium"));
        */


        recyclerView.setAdapter(new ClassDetailsAdapter(classes));

        return view;
    }
}