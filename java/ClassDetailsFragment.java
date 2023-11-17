package com.example.testing;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.checkerframework.checker.units.qual.A;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ClassDetailsFragment extends Fragment implements  ClassDetailsInterface{

    private RecyclerView recyclerView;
    private ArrayList<MyClass> classes;
    private DatabaseManager dbManager;
    private Account account;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_class_details, container, false);
        dbManager = new DatabaseManager(requireActivity());
        account = new Account();

        try {
            dbManager.open();
        } catch (Exception e){
            e.printStackTrace();
        }

        Bundle args = getArguments();
        if (args != null && args.containsKey("account")) {
            account = (Account) args.getSerializable("account");
        }

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        classes = new ArrayList<>();

        classes = dbManager.getAvailableClasses(account.getAccountDBID());
        dbManager.close();


        recyclerView.setAdapter(new ClassDetailsAdapter(classes, this::onItemClick));

        return view;
    }

    @Override
    public void onItemClick(int position) {
        MyClass myClass = new MyClass(classes.get(position).getTrainerDetails(),
                classes.get(position).getClassDBID(),
                classes.get(position).getClassStartTime(),
                classes.get(position).getClassEndTime(),
                classes.get(position).getClassDuration(),
                classes.get(position).getClassDate(),
                classes.get(position).getClassVenue(),
                classes.get(position).getClassDescription(),
                classes.get(position).getClassAvailability());

        Intent i = new Intent(requireActivity(), bookClass.class);
        i.putExtra("myClass", myClass);
        i.putExtra("account", account);
        startActivity(i);
    }
}