package com.example.testing;

import android.app.Activity;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HistoryFragment extends Fragment implements ClassDetailsInterface{

    private RecyclerView recyclerView;
    private ArrayList<MyClass> classes;
    private DatabaseManager dbManager;
    private Account account;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
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

        recyclerView = view.findViewById(R.id.recyclerViewHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        classes = new ArrayList<>();

        classes = dbManager.getHistoryClasses(account.getAccountDBID());
        dbManager.close();

        recyclerView.setAdapter(new ClassDetailsAdapter(classes, this::onItemClick));

        return view;
    }

    @Override
    public void onItemClick(int position) {

    }
}